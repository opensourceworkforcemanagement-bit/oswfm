package org.oswfm.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Security configuration for the BFF (Backend-for-Frontend) pattern.
 *
 * <p>Establishes session-based authentication: the browser identifies itself with an
 * {@code APPSESSION} HttpOnly cookie (managed by {@link SessionConfig}). The browser
 * never sends or stores raw JWT tokens.
 *
 * <p>Authorization rules (first match wins):
 * <ul>
 *   <li>BFF auth endpoints — public (login creates a session; me/logout work without auth enforcement here)
 *   <li>{@code /api/v1/authentication/**} — public (backward-compat for legacy/non-BFF clients)
 *   <li>{@code /api/**} — requires an active authenticated session
 * </ul>
 *
 * <p>CSRF protection uses {@link CookieServerCsrfTokenRepository#withHttpOnlyFalse()}:
 * the {@code XSRF-TOKEN} cookie is readable by JavaScript. The SPA reads it and sends
 * it as the {@code X-XSRF-TOKEN} header on every state-changing request.
 *
 * <p>The {@link WebSessionServerSecurityContextRepository} bean is exposed so that
 * {@code BffAuthController} can save the {@code SecurityContext} to the WebSession
 * immediately after a successful upstream login — the same pattern used by the exampleBFF.
 */
@Configuration
@EnableWebFluxSecurity
public class BffSecurityConfig {

    /**
     * Exposes the session-backed security context repository as a bean so
     * {@link org.oswfm.apigateway.controller.BffAuthController} can persist an
     * authenticated principal into the session after a successful upstream login.
     */
    @Bean
    WebSessionServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    /**
     * Ensures the XSRF-TOKEN cookie is written on every response.
     *
     * <p>With {@link ServerCsrfTokenRequestAttributeHandler}, the CSRF token is stored as a
     * lazy {@code Mono} attribute on the exchange. If nothing subscribes to it (e.g. on a GET
     * request), {@link CookieServerCsrfTokenRepository} never writes the cookie. This filter
     * explicitly subscribes on every response so the cookie is always present for the SPA to read.
     */
    @Bean
    WebFilter csrfCookieWebFilter() {
        return (exchange, chain) -> {
            Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
            if (csrfToken != null) {
                return csrfToken.doOnSuccess(token -> {
                    // Subscribing causes CookieServerCsrfTokenRepository to write the cookie
                }).then(chain.filter(exchange));
            }
            return chain.filter(exchange);
        };
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            WebSessionServerSecurityContextRepository securityContextRepository) {

        return http
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchanges -> exchanges
                        // BFF auth endpoints — public
                        .pathMatchers(HttpMethod.POST, "/api/bff/auth/login").permitAll()
                        .pathMatchers(HttpMethod.GET,  "/api/bff/auth/me").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/bff/auth/logout").permitAll()
                        // Legacy auth-service proxy routes — public (backward compatibility)
                        .pathMatchers("/api/v1/authentication/**").permitAll()
                        // All other API routes require an authenticated session
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().authenticated()
                )
                // Cookie-based CSRF: XSRF-TOKEN readable by JS (not HttpOnly).
                // SPA reads document.cookie for "XSRF-TOKEN" and sends as X-XSRF-TOKEN header.
                // Login is excluded: it has no session yet so there is no CSRF state to protect.
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
                        .requireCsrfProtectionMatcher(exchange -> {
                            // Only protect state-changing methods (POST/PUT/DELETE/PATCH)
                            String method = exchange.getRequest().getMethod().name();
                            boolean isSafeMethod = method.equals("GET") || method.equals("HEAD")
                                    || method.equals("OPTIONS") || method.equals("TRACE");
                            if (isSafeMethod) {
                                return ServerWebExchangeMatcher.MatchResult.notMatch();
                            }
                            // Skip CSRF for login (unauthenticated — no session/cookie yet)
                            ServerWebExchangeMatcher loginMatcher =
                                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/api/bff/auth/login");
                            return loginMatcher.matches(exchange)
                                    .flatMap(result -> result.isMatch()
                                            ? ServerWebExchangeMatcher.MatchResult.notMatch()
                                            : ServerWebExchangeMatcher.MatchResult.match());
                        })
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
}
