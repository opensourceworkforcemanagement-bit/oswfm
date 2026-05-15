package org.oswfm.notificationservice.kafka;

import org.oswfm.commons.model.common.NotificationRequest;
import org.oswfm.kafkaserviceclient.exception.TopicAlreadyExistsException;
import org.oswfm.kafkaserviceclient.model.TopicCreateRequest;
import org.oswfm.kafkaserviceclient.service.TopicManagementService;
import org.oswfm.kafkaserviceclient.service.TopicSubscriptionService;
import org.oswfm.notificationservice.handler.NotificationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manages the {@code BroadCasters_Found_In_AOI} Kafka topic for the notification service.
 *
 * <p>On startup creates the topic (no-op if it already exists) and subscribes so incoming
 * messages are forwarded to targeted WebSocket clients.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BroadcastersInAoiKafkaService {

    static final String TOPIC = "BroadCasters_Found_In_AOI";
    private static final String SUBSCRIBER_ID = "notificationservice-aoi-handler";

    private final TopicManagementService topicManagementService;
    private final TopicSubscriptionService topicSubscriptionService;
    private final NotificationHandler notificationHandler;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        ensureTopicExists();
        subscribeToTopic();
    }

    private void ensureTopicExists() {
        TopicCreateRequest request = new TopicCreateRequest();
        request.setTopicName(TOPIC);
        request.setPartitions(1);
        request.setReplicationFactor((short) 1);

        try {
            topicManagementService.createTopic(request);
            log.info("[BroadcastersInAoi] Created topic={}", TOPIC);
        } catch (TopicAlreadyExistsException e) {
            log.info("[BroadcastersInAoi] Topic already exists, skipping creation: {}", TOPIC);
        }
    }

    private void subscribeToTopic() {
        topicSubscriptionService.subscribe(TOPIC, SUBSCRIBER_ID, event -> {
            log.debug("[BroadcastersInAoi] Received message on topic={} offset={}", event.getTopic(), event.getOffset());
            try {
                NotificationRequest message = objectMapper.readValue(event.getPayload(), NotificationRequest.class);

                if (message.getTargetUserIds() == null || message.getTargetUserIds().isEmpty()) {
                    log.warn("[BroadcastersInAoi] Message has no targetUserIds, skipping push");
                    return;
                }

                java.util.List<String> targetUserIds = message.getTargetUserIds();
                message.setTargetUserIds(null);
                String json = objectMapper.writeValueAsString(message);
                notificationHandler.pushToUsers(json, targetUserIds);

            } catch (java.io.IOException e) {
                log.error("[BroadcastersInAoi] Failed to process message: {}", e.getMessage(), e);
            }
        });
        log.info("[BroadcastersInAoi] Subscribed to topic={}", TOPIC);
    }
}
