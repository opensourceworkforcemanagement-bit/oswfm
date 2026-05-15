package org.oswfm.kafkaserviceclient.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TopicCreateRequest {

    @NotBlank(message = "topicName is required")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "topicName may only contain letters, digits, dots, underscores, and hyphens")
    private String topicName;

    @Positive
    private int partitions = 1;

    private short replicationFactor = 1;

    /** Optional retention period in milliseconds. -1 means use broker default. */
    private Long retentionMs;
}
