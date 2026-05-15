package org.oswfm.kafkaserviceclient.model;

import lombok.Builder;
import lombok.Data;

/**
 * Envelope delivered to {@link org.oswfm.kafkaserviceclient.service.TopicSubscriptionService}
 * message listeners when a Kafka record arrives on a subscribed topic.
 */
@Data
@Builder
public class KafkaMessageEvent {
    private String topic;
    private int partition;
    private long offset;
    private String key;
    private String payload;
    private long timestamp;
}
