package org.oswfm.kafkaserviceclient.service;

import org.oswfm.kafkaserviceclient.exception.TopicAlreadyExistsException;
import org.oswfm.kafkaserviceclient.exception.TopicNotFoundException;
import org.oswfm.kafkaserviceclient.model.TopicCreateRequest;
import org.oswfm.kafkaserviceclient.model.TopicInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicManagementService {

    private final AdminClient kafkaAdminClient;

    public TopicInfo createTopic(TopicCreateRequest request) {
        String topicName = request.getTopicName();
        try {
            Set<String> existing = kafkaAdminClient.listTopics().names().get();
            if (existing.contains(topicName)) {
                throw new TopicAlreadyExistsException("Topic already exists: " + topicName);
            }

            Map<String, String> configs = new HashMap<>();
            if (request.getRetentionMs() != null) {
                configs.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(request.getRetentionMs()));
            }

            NewTopic newTopic = new NewTopic(topicName, request.getPartitions(), request.getReplicationFactor());
            if (!configs.isEmpty()) {
                newTopic.configs(configs);
            }

            kafkaAdminClient.createTopics(List.of(newTopic)).all().get();
            log.info("[TopicMgmt] Created topic={} partitions={} replication={}", topicName, request.getPartitions(), request.getReplicationFactor());

            return TopicInfo.builder()
                    .topicName(topicName)
                    .partitions(request.getPartitions())
                    .replicationFactor(request.getReplicationFactor())
                    .configs(configs)
                    .subscriberCount(0)
                    .build();
        } catch (TopicAlreadyExistsException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while creating topic: " + topicName, e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to create topic: " + topicName, e.getCause());
        }
    }

    public List<TopicInfo> listTopics() {
        try {
            Set<String> names = kafkaAdminClient.listTopics(
                    new ListTopicsOptions().listInternal(false)
            ).names().get();

            List<TopicInfo> result = new ArrayList<>();
            for (String name : names) {
                try {
                    result.add(describeTopic(name));
                } catch (Exception e) {
                    log.warn("[TopicMgmt] Could not describe topic={}: {}", name, e.getMessage());
                }
            }
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while listing topics", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to list topics", e.getCause());
        }
    }

    public TopicInfo describeTopic(String topicName) {
        try {
            DescribeTopicsResult describeResult = kafkaAdminClient.describeTopics(List.of(topicName));
            TopicDescription description = describeResult.topicNameValues().get(topicName).get();

            DescribeConfigsResult configsResult = kafkaAdminClient.describeConfigs(
                    List.of(new ConfigResource(ConfigResource.Type.TOPIC, topicName))
            );
            Config config = configsResult.values()
                    .get(new ConfigResource(ConfigResource.Type.TOPIC, topicName))
                    .get();

            Map<String, String> configMap = config.entries().stream()
                    .filter(e -> !e.isDefault())
                    .collect(Collectors.toMap(ConfigEntry::name, ConfigEntry::value));

            int partitions = description.partitions().size();
            short replicationFactor = (short) description.partitions().get(0).replicas().size();

            return TopicInfo.builder()
                    .topicName(topicName)
                    .partitions(partitions)
                    .replicationFactor(replicationFactor)
                    .configs(configMap)
                    .subscriberCount(0)
                    .build();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while describing topic: " + topicName, e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof org.apache.kafka.common.errors.UnknownTopicOrPartitionException) {
                throw new TopicNotFoundException("Topic not found: " + topicName);
            }
            throw new RuntimeException("Failed to describe topic: " + topicName, cause);
        }
    }

    public void deleteTopic(String topicName) {
        try {
            Set<String> existing = kafkaAdminClient.listTopics().names().get();
            if (!existing.contains(topicName)) {
                throw new TopicNotFoundException("Topic not found: " + topicName);
            }

            kafkaAdminClient.deleteTopics(List.of(topicName)).all().get();
            log.info("[TopicMgmt] Deleted topic={}", topicName);
        } catch (TopicNotFoundException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while deleting topic: " + topicName, e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to delete topic: " + topicName, e.getCause());
        }
    }
}
