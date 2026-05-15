package org.oswfm.gisservice.kafka;

import java.util.List;
import java.util.Map;

import org.oswfm.commons.model.common.NotificationRequest;
import org.oswfm.commons.model.common.RestMessageRequest.Payload;
import org.oswfm.gisservice.dto.UserPositionDTO;
import org.oswfm.gisservice.service.UserPositionService;
import org.oswfm.kafkaserviceclient.exception.TopicAlreadyExistsException;
import org.oswfm.kafkaserviceclient.model.TopicCreateRequest;
import org.oswfm.kafkaserviceclient.service.KafkaPublisherService;
import org.oswfm.kafkaserviceclient.service.TopicManagementService;
import org.oswfm.kafkaserviceclient.service.TopicSubscriptionService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BroadcastRequestKafkaService {

    static final String TOPIC = "Broadcast_Request";
    static final String AOI_TOPIC = "BroadCasters_Found_In_AOI";
    private static final String SUBSCRIBER_ID = "gisservice-broadcast-handler";

    private final TopicManagementService topicManagementService;
    private final TopicSubscriptionService topicSubscriptionService;
    private final KafkaPublisherService kafkaPublisherService;
    private final UserPositionService userPositionService;
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
            log.info("[BroadcastRequest] Created topic={}", TOPIC);
        } catch (TopicAlreadyExistsException e) {
            log.info("[BroadcastRequest] Topic already exists, skipping creation: {}", TOPIC);
        }
    }

    private void subscribeToTopic() {
        topicSubscriptionService.subscribe(TOPIC, SUBSCRIBER_ID, event -> {
            log.debug("[BroadcastRequest] Received message on topic={} offset={} payload={}", event.getTopic(), event.getOffset(), event.getPayload());
            try {
                NotificationRequest request = objectMapper.readValue(
                        event.getPayload(), NotificationRequest.class);

                Map<String, String> data = request.getPayload() != null && request.getPayload().getData() != null
                        ? request.getPayload().getData()
                        : Map.of();

                double lat = toDouble(data.get("lat"));
                double lon = toDouble(data.get("lon"));
                double distance = toDouble(data.get("distanceMeters"));
                String budget = String.valueOf(data.get("budget"));

                List<UserPositionDTO> usersInAoi = userPositionService.findUsersWithinDistance(lat, lon, distance);
                log.info("[BroadcastRequest] Found {} user(s) within {}m of ({},{}) for sender={}",
                        usersInAoi.size(), distance, lat, lon, request.getRequestingUserId());

                if (!usersInAoi.isEmpty()) {
                    List<String> userIds = usersInAoi.stream()
                            .map(u -> String.valueOf(u.getUserId()))
                            .toList();

                    Payload aoiPayload = new Payload();
                    aoiPayload.setTitle("Broadcasters found in AOI");
                    aoiPayload.setBody(usersInAoi.size() + " broadcaster(s) found within " + distance + "m");
                    aoiPayload.setData(Map.of(
                            "lat", String.valueOf(lat),
                            "lon", String.valueOf(lon),
                            "distanceMeters", String.valueOf(distance),
                            "budget", budget
                    ));

                    NotificationRequest aoiMessage = new NotificationRequest();
                    aoiMessage.setType(TOPIC);
                    aoiMessage.setSource("gisservice");
                    aoiMessage.setRequestingUserId(request.getRequestingUserId());
                    aoiMessage.setTargetUserIds(userIds);
                    aoiMessage.setPayload(aoiPayload);

                    kafkaPublisherService.publishPayload(AOI_TOPIC, aoiMessage);
                    log.info("[BroadcastRequest] Published {} user(s) to topic={}", usersInAoi.size(), AOI_TOPIC);
                } else {
                    log.info("[BroadcastRequest] No users found within {}m of ({},{}) for sender={}",
                            distance, lat, lon, request.getRequestingUserId());
                }

            } catch (JsonProcessingException e) {
                log.error("[BroadcastRequest] Failed to deserialize message: {}", e.getMessage(), e);
            } catch (RuntimeException e) {
                log.error("[BroadcastRequest] Failed to process message: {}", e.getMessage(), e);
            }
        });
        log.info("[BroadcastRequest] Subscribed to topic={}", TOPIC);
    }

    private double toDouble(Object value) {
        if (value == null) return 0.0;
        if (value instanceof Number n) return n.doubleValue();
        return Double.parseDouble(String.valueOf(value));
    }
}
