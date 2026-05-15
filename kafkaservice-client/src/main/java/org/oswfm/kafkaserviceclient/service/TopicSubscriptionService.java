package org.oswfm.kafkaserviceclient.service;

import org.oswfm.kafkaserviceclient.model.KafkaMessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicSubscriptionService {

    private final ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    /** key = "topicName::subscriberId" → running container */
    private final Map<String, ConcurrentMessageListenerContainer<String, String>> containers =
            new ConcurrentHashMap<>();

    public synchronized void subscribe(String topicName, String subscriberId, Consumer<KafkaMessageEvent> handler) {
        String key = containerKey(topicName, subscriberId);
        if (containers.containsKey(key)) {
            throw new IllegalStateException(
                    "Subscription already exists for topic=" + topicName + " subscriberId=" + subscriberId);
        }

        ContainerProperties props = new ContainerProperties(topicName);
        props.setGroupId("kafkaclient-" + subscriberId + "-" + topicName);
        props.setMessageListener((MessageListener<String, String>) record -> {
            KafkaMessageEvent event = toEvent(record);
            try {
                handler.accept(event);
            } catch (Exception e) {
                log.error("[Subscription] Handler threw for topic={} subscriberId={}: {}",
                        topicName, subscriberId, e.getMessage(), e);
            }
        });

        ConcurrentMessageListenerContainer<String, String> container =
                kafkaListenerContainerFactory.createContainer(topicName);
        container.getContainerProperties().setGroupId("kafkaclient-" + subscriberId + "-" + topicName);
        container.getContainerProperties().setMessageListener(
                (MessageListener<String, String>) record -> {
                    KafkaMessageEvent event = toEvent(record);
                    try {
                        handler.accept(event);
                    } catch (Exception e) {
                        log.error("[Subscription] Handler threw for topic={} subscriberId={}: {}",
                                topicName, subscriberId, e.getMessage(), e);
                    }
                });
        container.start();
        containers.put(key, container);
        log.info("[Subscription] Subscribed topic={} subscriberId={}", topicName, subscriberId);
    }

    public synchronized void unsubscribe(String topicName, String subscriberId) {
        String key = containerKey(topicName, subscriberId);
        ConcurrentMessageListenerContainer<String, String> container = containers.remove(key);
        if (container != null) {
            container.stop();
            log.info("[Subscription] Unsubscribed topic={} subscriberId={}", topicName, subscriberId);
        }
    }

    public synchronized void unsubscribeAll(String subscriberId) {
        String suffix = "::" + subscriberId;
        containers.entrySet().removeIf(entry -> {
            if (entry.getKey().endsWith(suffix)) {
                entry.getValue().stop();
                log.info("[Subscription] Unsubscribed (all) key={}", entry.getKey());
                return true;
            }
            return false;
        });
    }

    public boolean isSubscribed(String topicName, String subscriberId) {
        return containers.containsKey(containerKey(topicName, subscriberId));
    }

    private static String containerKey(String topicName, String subscriberId) {
        return topicName + "::" + subscriberId;
    }

    private static KafkaMessageEvent toEvent(ConsumerRecord<String, String> record) {
        return KafkaMessageEvent.builder()
                .topic(record.topic())
                .partition(record.partition())
                .offset(record.offset())
                .key(record.key())
                .payload(record.value())
                .timestamp(record.timestamp())
                .build();
    }
}
