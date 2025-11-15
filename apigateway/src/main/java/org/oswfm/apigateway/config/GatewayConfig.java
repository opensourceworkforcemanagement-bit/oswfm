package org.oswfm.apigateway.config;

import java.util.List;

import org.oswfm.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class named {@link GatewayConfig} for setting up API Gateway routes.
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Define the list of public endpoints
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/authentication/users/register",
            "/api/v1/authentication/users/login",
            "/api/v1/authentication/users/refresh-token",
            "/api/v1/authentication/users/logout"
    );

    /**
     * Configures the route locator to define the routing rules for the gateway.
     *
     * @param builder The RouteLocatorBuilder used to build the RouteLocator.
     * @return A RouteLocator with the defined routes.
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("timesheetservice", r -> r.path("/api/v1/timesheets/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://timesheetservice"))
                .route("authservice", r -> r.path("/api/v1/authentication/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://authservice"))
                .route("userservice", r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://userservice"))
                 .route("employees", r -> r.path("/api/v1/employees/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://userservice"))                       
                .build();
    }

    /**
     * Configures CORS settings for the application.
     * Development settings allow all origins, methods, and headers.
     * @return A CorsConfigurationSource with the defined CORS settings.
     */
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//         configuration.setAllowedMethods(Arrays.asList("*"));
//         configuration.setAllowedHeaders(Arrays.asList("*"));
//         configuration.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }

//     @Bean
//     public CorsWebFilter corsWebFilter() {
//         CorsConfiguration corsConfig = new CorsConfiguration();
//         corsConfig.setAllowCredentials(false);
        
//         // Add React Native Metro bundler origin
//         corsConfig.setAllowedOrigins(Arrays.asList(
//             "http://localhost:8081",  // Metro bundler
//             "http://localhost:19006", // Expo web
//             "http://localhost:3000",  // Common React dev server
//             "*"  // Allow all for development
//         ));
        
//         corsConfig.setAllowedHeaders(Arrays.asList("*"));
//         corsConfig.setAllowedMethods(Arrays.asList(
//             "GET", "POST", "PUT", "DELETE", "OPTIONS"
//         ));
//         corsConfig.setMaxAge(3600L);
        
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", corsConfig);
        
//         return new CorsWebFilter(source);
//     }
}