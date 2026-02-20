package org.oswfm.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.web.server.session.SpringSessionWebSessionStore;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configures Spring Session for the reactive (WebFlux/Gateway) stack without using
 * {@code @EnableSpringWebSession}, which auto-registers its own {@code webSessionManager}
 * bean and conflicts with a custom one.
 *
 * <p>We register the repository and session manager beans directly so we can fully
 * control the APPSESSION cookie (name, HttpOnly, SameSite, path).
 *
 * <p>Cookie settings:
 * <ul>
 *   <li>Name: {@code APPSESSION}
 *   <li>HttpOnly: {@code true} — not accessible to JavaScript
 *   <li>Secure: {@code false} — set to {@code true} when HTTPS is configured
 *   <li>SameSite: {@code Lax} — allows cross-port dev requests (use Strict in production)
 *   <li>Path: {@code /}
 * </ul>
 */
@Configuration
public class SessionConfig {

    private static final int SESSION_TIMEOUT_SECONDS = 1800; // 30 minutes

    @Bean
    public ReactiveSessionRepository<MapSession> reactiveSessionRepository() {
        var repository = new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
        repository.setDefaultMaxInactiveInterval(Duration.ofSeconds(SESSION_TIMEOUT_SECONDS));
        return repository;
    }

    @Bean
    public WebSessionManager webSessionManager(ReactiveSessionRepository<MapSession> repository) {
        var sessionStore = new SpringSessionWebSessionStore<>(repository);

        var idResolver = new CookieWebSessionIdResolver();
        idResolver.setCookieName("APPSESSION");
        idResolver.addCookieInitializer(builder -> builder
                .httpOnly(true)
                .secure(false)      // Set to true in production (requires HTTPS)
                .sameSite("Lax")   // Use Strict in production; Lax allows cross-port in dev
                .path("/")
        );

        var manager = new DefaultWebSessionManager();
        manager.setSessionStore(sessionStore);
        manager.setSessionIdResolver(idResolver);

        return manager;
    }
}
