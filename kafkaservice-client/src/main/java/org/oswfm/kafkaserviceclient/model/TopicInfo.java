package org.oswfm.kafkaserviceclient.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TopicInfo {
    private String topicName;
    private int partitions;
    private short replicationFactor;
    private Map<String, String> configs;
    private int subscriberCount;
}
