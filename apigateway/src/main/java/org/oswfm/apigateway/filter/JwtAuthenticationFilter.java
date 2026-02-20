package org.oswfm.apigateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oswfm.apigateway.client.UserServiceWebClient;
import org.oswfm.apigateway.model.BffSessionKeys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * BFF Token Injection Filter — replaces the old Bearer-validation filter.
 *
 * <p><strong>Before (old behaviour):</strong> extracted a Bearer token from the incoming
 * request header and validated it against the userservice. Rejected requests with no token.
 *
 * <p><strong>After (new behaviour):</strong> reads {@code ACCESS_TOKEN} from the server-side
 * {@link org.springframework.web.server.WebSession} and injects it as
 * {@code Authorization: Bearer <token>} into the mutated upstream request.
 * The browser never sends or stores the JWT — it only sends the {@code APPSESSION} cookie.
 *
 * <p>Transparent token refresh: if the stored access token is within 60 seconds of expiry
 * (determined by Base64-decoding the JWT payload — no crypto needed since the token was
 * already validated at login time), the filter proactively refreshes it via userservice
 * before forwarding the request. On refresh failure the session is invalidated and 401 returned.
 *
 * <p>Public endpoints (configured via {@link Config#publicEndpoints}) bypass the filter entirely.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final int REFRESH_BUFFER_SECONDS = 60;

    private final UserServiceWebClient userServiceWebClient;

    // -------------------------------------------------------------------------
    // Config
    // -------------------------------------------------------------------------

    public static class Config {
        private List<String> publicEndpoints;

        public List<String> getPublicEndpoints() {
            return publicEndpoints;
        }

        public Config setPublicEndpoints(List<String> publicEndpoints) {
            this.publicEndpoints = publicEndpoints;
            return this;
        }
    }

    // -------------------------------------------------------------------------
    // Filter logic
    // -------------------------------------------------------------------------

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // Pass public endpoints straight through — no session required
            if (config != null && config.getPublicEndpoints().stream().anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            return exchange.getSession().flatMap(session -> {
                String accessToken  = session.getAttribute(BffSessionKeys.ACCESS_TOKEN);
                String refreshToken = session.getAttribute(BffSessionKeys.REFRESH_TOKEN);

                if (accessToken == null) {
                    log.warn("JwtAuthenticationFilter | no ACCESS_TOKEN in session for path: {}", path);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                // Proactively refresh if token expires within the buffer window
                if (isNearExpiry(accessToken)) {
                    if (refreshToken == null) {
                        log.warn("JwtAuthenticationFilter | access token near expiry but no refresh token in session");
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                    log.debug("JwtAuthenticationFilter | proactive token refresh for path: {}", path);
                    return userServiceWebClient.refreshToken(refreshToken)
                            .flatMap(refreshResponse -> {
                                Object responseObj = refreshResponse.get("response");
                                if (!(responseObj instanceof Map<?, ?> tokenData)) {
                                    log.error("JwtAuthenticationFilter | refresh response missing 'response' field");
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }
                                String newAccessToken = String.valueOf(tokenData.get("accessToken"));
                                session.getAttributes().put(BffSessionKeys.ACCESS_TOKEN, newAccessToken);
                                return injectAndForward(exchange, chain, newAccessToken, path);
                            })
                            .onErrorResume(e -> {
                                log.error("JwtAuthenticationFilter | token refresh failed, invalidating session", e);
                                return session.invalidate().then(Mono.defer(() -> {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }));
                            });
                }

                return injectAndForward(exchange, chain, accessToken, path);
            });
        };
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private Mono<Void> injectAndForward(
            ServerWebExchange exchange,
            GatewayFilterChain chain,
            String accessToken,
            String path) {

        log.debug("JwtAuthenticationFilter | injecting Bearer token for path: {}", path);
        var mutatedRequest = exchange.getRequest().mutate()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * Determines if the JWT access token expires within {@link #REFRESH_BUFFER_SECONDS}.
     * Decodes the Base64-URL payload without cryptographic verification — the token was
     * validated by the userservice at login time, so we only need the {@code exp} claim.
     */
    private boolean isNearExpiry(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) return false;
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            int expIdx = payload.indexOf("\"exp\":");
            if (expIdx == -1) return false;
            String after = payload.substring(expIdx + 6).replaceAll("[^0-9].*", "");
            long exp = Long.parseLong(after.trim());
            long secondsUntilExpiry = exp - (System.currentTimeMillis() / 1000);
            return secondsUntilExpiry < REFRESH_BUFFER_SECONDS;
        } catch (Exception e) {
            log.debug("JwtAuthenticationFilter | could not parse exp from token, skipping refresh check");
            return false;
        }
    }
}