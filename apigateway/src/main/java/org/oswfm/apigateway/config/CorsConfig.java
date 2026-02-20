package org.oswfm.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS configuration for the BFF.
 *
 * <p>{@code allowCredentials} must be {@code true} so the browser sends the
 * {@code APPSESSION} and {@code XSRF-TOKEN} cookies on cross-origin requests.
 * With credentials enabled, wildcard origins are not permitted — explicit origins are required.
 *
 * <p>The {@code X-XSRF-TOKEN} header must be allowed so the SPA can send the
 * CSRF token it reads from the {@code XSRF-TOKEN} cookie.
 */
@Configuration
public class CorsConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Required for cookie-based auth: cannot combine credentials=true with wildcard origin
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of(
                "http://localhost:3000",   // Host MFE dev server
                "http://localhost:3001",   // Usermanagement MFE (if run standalone)
                "http://localhost:3002",   // Timesheetmanagement MFE (if run standalone)
                "http://localhost:3003",   // Administrationmanagement MFE (if run standalone)
                "http://localhost:8080"    // Alternative / reverse-proxy port
        ));

        corsConfig.setAllowedHeaders(Arrays.asList(
                "Content-Type",
                "Accept",
                "X-XSRF-TOKEN",       // CSRF token read from cookie and sent as header by SPA
                "Authorization",      // Kept for any direct API clients (Postman, mobile, etc.)
                "X-Requested-With"
        ));

        corsConfig.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        corsConfig.setExposedHeaders(List.of("Set-Cookie"));
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}



