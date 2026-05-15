package org.oswfm.kafkaserviceclient.service;

import org.oswfm.kafkaserviceclient.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisherService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String topicName, PublishRequest request) {
        String payloadJson = toJson(request.getPayload());

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, request.getKey(), payloadJson);

        if (request.getHeaders() != null) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                record.headers().add(new RecordHeader(
                        entry.getKey(),
                        entry.getValue().getBytes(StandardCharsets.UTF_8)));
            }
        }

        send(topicName, record);
    }

    public void publishPayload(String topicName, Object payload) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, null, toJson(payload));
        send(topicName, record);
    }

    public void publishPayload(String topicName, String key, Object payload) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, toJson(payload));
        send(topicName, record);
    }

    private void send(String topicName, ProducerRecord<String, String> record) {
        kafkaTemplate.send(record)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[Publisher] Failed to send to topic={}: {}", topicName, ex.getMessage());
                    } else {
                        log.debug("[Publisher] Sent to topic={} partition={} offset={}",
                                topicName,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    private String toJson(Object value) {
        if (value == null) return "null";
        if (value instanceof String s) return s;
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize payload to JSON", e);
        }
    }
}
