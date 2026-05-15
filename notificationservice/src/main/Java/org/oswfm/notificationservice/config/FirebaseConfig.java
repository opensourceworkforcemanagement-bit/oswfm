package org.oswfm.notificationservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials-path:}")
    private String credentialsPath;

    @Value("${firebase.credentials-resource:classpath:firebase-service-account.json}")
    private Resource credentialsResource;

    @PostConstruct
    public void init() {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.info("[Firebase] Already initialized");
            return;
        }

        try {
            InputStream stream = resolveCredentials();
            if (stream == null) {
                log.warn("[Firebase] No credentials found — FCM push disabled. " +
                        "Set firebase.credentials-path or place firebase-service-account.json on the classpath.");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("[Firebase] Admin SDK initialized successfully");
        } catch (IOException e) {
            log.error("[Firebase] Failed to initialize Admin SDK: {}", e.getMessage());
        }
    }

    private InputStream resolveCredentials() throws IOException {
        if (credentialsPath != null && !credentialsPath.isBlank()) {
            var file = new java.io.File(credentialsPath);
            if (file.exists()) {
                log.info("[Firebase] Loading credentials from path: {}", credentialsPath);
                return new java.io.FileInputStream(file);
            }
        }

        try {
            if (credentialsResource.exists()) {
                log.info("[Firebase] Loading credentials from classpath resource");
                return credentialsResource.getInputStream();
            }
        } catch (Exception ignored) {}

        return null;
    }
}
