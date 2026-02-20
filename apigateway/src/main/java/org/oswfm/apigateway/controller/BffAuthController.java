package org.oswfm.apigateway.controller;

import java.util.Collections;
import java.util.Map;

import org.oswfm.apigateway.client.UserServiceWebClient;
import org.oswfm.apigateway.model.BffLoginRequest;
import org.oswfm.apigateway.model.BffSessionKeys;
import org.oswfm.apigateway.model.BffUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * BFF (Backend-for-Frontend) authentication controller — Token Vault pattern.
 *
 * <p>Mirrors the pattern in {@code exampleBFF/controller/AuthController.java}.
 * The browser calls these three endpoints; raw JWTs are never returned to the browser.
 *
 * <ul>
 *   <li>POST /api/bff/auth/login  — Credentials in, {@link BffUserInfo} out.
 *       Calls userservice, stores access + refresh tokens in the server-side {@link WebSession}.
 *   <li>GET  /api/bff/auth/me     — Returns user info from session, or 401 if no session.
 *   <li>POST /api/bff/auth/logout — Invalidates upstream tokens, then invalidates the session.
 * </ul>
 */
@RestController
@RequestMapping("/api/bff/auth")
@RequiredArgsConstructor
@Slf4j
public class BffAuthController {

    private final UserServiceWebClient userServiceWebClient;
    private final WebSessionServerSecurityContextRepository securityContextRepository;

    /**
     * POST /api/bff/auth/login
     *
     * <ol>
     *   <li>Forwards credentials to {@code POST /api/v1/users/login} on userservice.
     *   <li>Extracts {@code accessToken} and {@code refreshToken} from the response.
     *   <li>Calls {@code GET /api/v1/users/authenticate} to resolve user claims.
     *   <li>Stores both tokens and user info in the server-side {@link WebSession} (Token Vault).
     *   <li>Persists an authenticated {@code SecurityContext} so Spring Security marks the
     *       session as authenticated.
     *   <li>Returns only {@link BffUserInfo} — no tokens reach the browser.
     * </ol>
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<BffUserInfo>> login(
            @RequestBody BffLoginRequest request,
            ServerWebExchange exchange) {

        return userServiceWebClient.login(request.userName(), request.password())
                .flatMap(loginResponse -> {
                    // userservice returns CustomResponse<TokenResponse>:
                    // { "isSuccess": true, "response": { "accessToken": "...", "refreshToken": "...", ... } }
                    Object responseObj = loginResponse.get("response");
                    if (!(responseObj instanceof Map<?, ?> tokenData)) {
                        log.warn("BffAuthController | login | unexpected response shape");
                        return Mono.<ResponseEntity<BffUserInfo>>just(
                                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                    }

                    String accessToken  = String.valueOf(tokenData.get("accessToken"));
                    String refreshToken = String.valueOf(tokenData.get("refreshToken"));

                    // Call authenticate endpoint to get user claims from the JWT
                    return userServiceWebClient.authenticate(accessToken)
                            .flatMap(authResponse -> {
                                // UsernamePasswordAuthenticationToken is serialised as a Map by Jackson.
                                // Claims are in the "details" field which is also a Map.
                                BffUserInfo userInfo = extractUserInfo(authResponse, tokenData);

                                return exchange.getSession()
                                        .flatMap(session -> {
                                            // Token Vault: store tokens server-side only
                                            session.getAttributes().put(BffSessionKeys.ACCESS_TOKEN,  accessToken);
                                            session.getAttributes().put(BffSessionKeys.REFRESH_TOKEN, refreshToken);
                                            session.getAttributes().put(BffSessionKeys.USER_INFO,     userInfo);

                                            // Persist authenticated SecurityContext to the session so
                                            // Spring Security marks this session as authenticated.
                                            var auth = new UsernamePasswordAuthenticationToken(
                                                    userInfo.username(), null, Collections.emptyList());
                                            //auth.setAuthenticated(true);
                                            var secCtx = new SecurityContextImpl(auth);

                                            return securityContextRepository.save(exchange, secCtx)
                                                    .thenReturn(ResponseEntity.ok(userInfo));
                                        });
                            })
                            .onErrorResume(e -> {
                                log.error("BffAuthController | authenticate call failed", e);
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                            });
                })
                .onErrorResume(e -> {
                    log.error("BffAuthController | login failed", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }

    /**
     * GET /api/bff/auth/me
     *
     * <p>Returns user info stored in the current session. The {@code BffSecurityConfig}
     * permits this path so unauthenticated callers reach this endpoint and receive 401,
     * which the SPA uses to determine whether a session is still active on page load.
     */
    @GetMapping("/me")
    public Mono<ResponseEntity<BffUserInfo>> me(WebSession session) {
        BffUserInfo userInfo = (BffUserInfo) session.getAttribute(BffSessionKeys.USER_INFO);
        if (userInfo == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        return Mono.just(ResponseEntity.ok(userInfo));
    }

    /**
     * POST /api/bff/auth/logout
     *
     * <ol>
     *   <li>Reads both tokens from the session.
     *   <li>Calls userservice logout to add them to the token blacklist (best-effort).
     *   <li>Invalidates the server-side session — Spring clears the {@code APPSESSION} cookie.
     * </ol>
     */
    @PostMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(WebSession session) {
        String accessToken  = session.getAttribute(BffSessionKeys.ACCESS_TOKEN);
        String refreshToken = session.getAttribute(BffSessionKeys.REFRESH_TOKEN);

        Mono<Void> upstreamLogout = (accessToken != null && refreshToken != null)
                ? userServiceWebClient.logout(accessToken, refreshToken)
                        .onErrorResume(e -> {
                            log.warn("BffAuthController | upstream logout failed, continuing", e);
                            return Mono.empty();
                        })
                : Mono.empty();

        return upstreamLogout
                .then(session.invalidate())
                .thenReturn(ResponseEntity.<Void>noContent().build());
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Extracts {@link BffUserInfo} from the authenticate endpoint response.
     *
     * <p>The userservice {@code GET /api/v1/users/authenticate} returns a
     * {@code UsernamePasswordAuthenticationToken} serialised as JSON by Jackson. The principal
     * is a Spring Security {@code Jwt} object, so claims live under:
     * <pre>{ "principal": { "claims": { "userId": "...", ... } } }</pre>
     *
     * <p>Falls back to the {@code "details"} field and then the raw token response map
     * if the primary path is absent.
     */
    @SuppressWarnings("unchecked")
    private BffUserInfo extractUserInfo(Map<String, Object> authResponse, Map<?, ?> tokenData) {
        // Primary: claims are under principal.claims (Spring Jwt serialisation)
        Object principal = authResponse.get("principal");
        if (principal instanceof Map<?, ?> principalMap) {
            Object claimsObj = principalMap.get("claims");
            if (claimsObj instanceof Map<?, ?> claimsMap) {
                return new BffUserInfo(
                        safeString(claimsMap.get("userId")),
                        safeString(claimsMap.get("username")),
                        safeString(claimsMap.get("userFirstName")),
                        safeString(claimsMap.get("userLastName")),
                        safeString(claimsMap.get("userType"))
                );
            }
        }

        // Secondary: some serialisers put claims in "details"
        Object details = authResponse.get("details");
        if (details instanceof Map<?, ?> claimsMap) {
            return new BffUserInfo(
                    safeString(claimsMap.get("userId")),
                    safeString(claimsMap.get("username")),
                    safeString(claimsMap.get("userFirstName")),
                    safeString(claimsMap.get("userLastName")),
                    safeString(claimsMap.get("userType"))
            );
        }

        // Fallback: use what we have from the token response
        log.warn("BffAuthController | extractUserInfo | could not find claims in principal or details; falling back to token data");
        return new BffUserInfo(
                safeString(tokenData.get("userId")),
                safeString(tokenData.get("username")),
                safeString(tokenData.get("userFirstName")),
                safeString(tokenData.get("userLastName")),
                safeString(tokenData.get("userType"))
        );
    }

    private String safeString(Object value) {
        return value != null ? String.valueOf(value) : "";
    }
}
