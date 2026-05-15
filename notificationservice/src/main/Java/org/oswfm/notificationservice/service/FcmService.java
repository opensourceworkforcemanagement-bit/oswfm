package org.oswfm.notificationservice.service;

import org.oswfm.notificationservice.model.DeviceToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final DeviceTokenStore deviceTokenStore;

    public void sendToAll(String type, String title, String body, Map<String, String> data) {
        var tokens = deviceTokenStore.getAll();
        if (tokens.isEmpty()) {
            log.info("[FcmService] No registered devices — skipping push");
            return;
        }
        for (DeviceToken deviceToken : tokens) {
            sendTo(deviceToken.getToken(), type, title, body, data);
        }
    }

    public void sendTo(String token, String type, String title, String body, Map<String, String> extraData) {
        try {
            Message.Builder builder = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("type", type);

            if (extraData != null) {
                extraData.forEach(builder::putData);
            }

            String messageId = FirebaseMessaging.getInstance().send(builder.build());
            log.info("[FcmService] Sent to token={}... messageId={}", token.substring(0, Math.min(20, token.length())), messageId);
        } catch (Exception e) {
            log.error("[FcmService] Failed to send to token={}...: {}", token.substring(0, Math.min(20, token.length())), e.getMessage());
        }
    }
}
