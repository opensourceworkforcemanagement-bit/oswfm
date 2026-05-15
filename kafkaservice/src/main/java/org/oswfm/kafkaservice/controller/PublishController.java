package org.oswfm.kafkaservice.controller;

import org.oswfm.kafkaserviceclient.model.PublishRequest;
import org.oswfm.kafkaserviceclient.service.KafkaPublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "Message Publishing", description = "Publish messages to Kafka topics")
public class PublishController {

    private final KafkaPublisherService kafkaPublisherService;

    @PostMapping("/{topicName}/messages")
    @Operation(
            summary = "Publish a message",
            description = "Publishes a message to the specified Kafka topic. " +
                    "All WebSocket subscribers connected to ws://<host>:1120/ws/topics/{topicName} will receive the message."
    )
    public ResponseEntity<Void> publish(
            @PathVariable String topicName,
            @RequestBody PublishRequest request) {
        kafkaPublisherService.publish(topicName, request);
        return ResponseEntity.accepted().build();
    }
}
