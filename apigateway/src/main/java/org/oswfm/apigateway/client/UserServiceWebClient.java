package org.oswfm.apigateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Reactive WebClient for calling userservice auth endpoints directly from the BFF layer.
 *
 * <p>Used exclusively by {@link org.oswfm.apigateway.controller.BffAuthController} to perform
 * login, token refresh, logout, and user-info lookups server-to-server.
 * The browser never sees the tokens returned by these calls.
 *
 * <p>Uses the Eureka load-balancer filter so the {@code lb://userservice} base URL
 * resolves to a live service instance via Spring Cloud LoadBalancer.
 */
@Component
@Slf4j
public class UserServiceWebClient {

    private final WebClient webClient;

    public UserServiceWebClient(
            @Value("${services.userservice-url:lb://userservice}") String userserviceUrl,
            ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.webClient = WebClient.builder()
                .baseUrl(userserviceUrl)
                .filter(lbFunction)
                .build();
    }

    /**
     * POST /api/v1/users/login
     * Returns the raw {@code CustomResponse<TokenResponse>} as a Map so the controller
     * can extract {@code accessToken}, {@code refreshToken}, and {@code accessTokenExpiresAt}.
     */
    public Mono<Map<String, Object>> login(String userName, String password) {
        log.debug("UserServiceWebClient | login | userName={}", userName);
        return webClient.post()
                .uri("/api/v1/users/login")
                .bodyValue(Map.of("userName", userName, "password", password))
                .retrieve()
                .bodyToMono(mapType())
                .doOnError(e -> log.error("UserServiceWebClient | login failed", e));
    }

    /**
     * POST /api/v1/users/refresh-token
     * Returns the raw response map containing the new {@code accessToken}.
     */
    public Mono<Map<String, Object>> refreshToken(String refreshToken) {
        log.debug("UserServiceWebClient | refreshToken");
        return webClient.post()
                .uri("/api/v1/users/refresh-token")
                .bodyValue(Map.of("refreshToken", refreshToken))
                .retrieve()
                .bodyToMono(mapType())
                .doOnError(e -> log.error("UserServiceWebClient | refreshToken failed", e));
    }

    /**
     * POST /api/v1/users/logout
     * Invalidates both tokens in the userservice token blacklist.
     */
    public Mono<Void> logout(String accessToken, String refreshToken) {
        log.debug("UserServiceWebClient | logout");
        return webClient.post()
                .uri("/api/v1/users/logout")
                .bodyValue(Map.of("accessToken", accessToken, "refreshToken", refreshToken))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> log.warn("UserServiceWebClient | logout upstream call failed (continuing)", e));
    }

    /**
     * GET /api/v1/users/authenticate?token={token}
     * Returns the authentication details (principal claims) for a given access token.
     * Used after login to retrieve user identity (userId, username, etc.) without
     * parsing the JWT client-side.
     */
    public Mono<Map<String, Object>> authenticate(String token) {
        log.debug("UserServiceWebClient | authenticate");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/users/authenticate")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(mapType())
                .doOnError(e -> log.error("UserServiceWebClient | authenticate failed", e));
    }

    @SuppressWarnings("unchecked")
    private Class<Map<String, Object>> mapType() {
        return (Class<Map<String, Object>>) (Class<?>) Map.class;
    }
}
