package org.oswfm.notificationservice.service;

import org.oswfm.notificationservice.model.DeviceToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class DeviceTokenStore {

    private final Map<String, DeviceToken> tokens = new ConcurrentHashMap<>();

    public void register(String token, String platform) {
        DeviceToken deviceToken = new DeviceToken(token, platform, Instant.now());
        tokens.put(token, deviceToken);
        log.info("[DeviceTokenStore] Registered token: platform={} token={}...", platform, token.substring(0, Math.min(20, token.length())));
    }

    public Collection<DeviceToken> getAll() {
        return tokens.values();
    }

    public int count() {
        return tokens.size();
    }
}
