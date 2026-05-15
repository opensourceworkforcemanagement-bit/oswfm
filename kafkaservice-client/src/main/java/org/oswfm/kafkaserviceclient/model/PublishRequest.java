package org.oswfm.kafkaserviceclient.model;

import lombok.Data;

import java.util.Map;

@Data
public class PublishRequest {

    /** Optional Kafka partition routing key. */
    private String key;

    /** Message payload — serialized to JSON when sent to Kafka. */
    private Object payload;

    /** Optional Kafka headers forwarded with the record. */
    private Map<String, String> headers;
}
