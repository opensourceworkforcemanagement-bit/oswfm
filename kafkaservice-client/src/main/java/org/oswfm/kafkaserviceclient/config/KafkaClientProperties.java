package org.oswfm.kafkaserviceclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the kafkaservice-client library.
 *
 * <p>All properties are prefixed with {@code oswfm.kafka}. They override
 * any values already set in the host service's {@code spring.kafka.*} config.
 *
 * <p>Example {@code application.yml}:
 * <pre>
 * oswfm:
 *   kafka:
 *     bootstrap-servers: localhost:9092
 *     consumer-group-prefix: myservice
 *     admin-request-timeout-ms: 10000
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "oswfm.kafka")
public class KafkaClientProperties {

    /**
     * Kafka bootstrap servers. Defaults to the value of
     * {@code spring.kafka.bootstrap-servers} if not explicitly set.
     */
    private String bootstrapServers;

    /**
     * Prefix used when constructing consumer group IDs for subscriptions.
     * Resulting group ID will be: {@code <prefix>-<subscriberId>-<topicName>}.
     * Defaults to "kafkaclient".
     */
    private String consumerGroupPrefix = "kafkaclient";

    /**
     * Timeout in milliseconds for AdminClient requests (create/delete/describe topics).
     */
    private int adminRequestTimeoutMs = 10000;

    /**
     * Default auto-offset-reset for new consumers created by {@link
     * org.oswfm.kafkaserviceclient.service.TopicSubscriptionService}.
     * Valid values: "earliest", "latest", "none".
     */
    private String autoOffsetReset = "earliest";
}
