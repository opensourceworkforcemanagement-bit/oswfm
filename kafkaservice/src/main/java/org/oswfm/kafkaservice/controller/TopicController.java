package org.oswfm.kafkaservice.controller;

import org.oswfm.kafkaserviceclient.model.TopicCreateRequest;
import org.oswfm.kafkaserviceclient.model.TopicInfo;
import org.oswfm.kafkaserviceclient.service.TopicManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "Topic Management", description = "Create, list, describe and delete Kafka topics")
public class TopicController {

    private final TopicManagementService topicManagementService;

    @GetMapping
    @Operation(summary = "List all topics", description = "Returns all non-internal topics registered on the Kafka broker")
    public ResponseEntity<List<TopicInfo>> listTopics() {
        return ResponseEntity.ok(topicManagementService.listTopics());
    }

    @PostMapping
    @Operation(summary = "Create a topic", description = "Creates a new Kafka topic with the specified configuration")
    public ResponseEntity<TopicInfo> createTopic(@Valid @RequestBody TopicCreateRequest request) {
        TopicInfo created = topicManagementService.createTopic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{topicName}")
    @Operation(summary = "Describe a topic", description = "Returns metadata and configuration for the specified topic")
    public ResponseEntity<TopicInfo> describeTopic(@PathVariable String topicName) {
        return ResponseEntity.ok(topicManagementService.describeTopic(topicName));
    }

    @DeleteMapping("/{topicName}")
    @Operation(summary = "Delete a topic", description = "Deletes the specified Kafka topic and disconnects all active subscribers")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topicName) {
        topicManagementService.deleteTopic(topicName);
        return ResponseEntity.noContent().build();
    }
}
