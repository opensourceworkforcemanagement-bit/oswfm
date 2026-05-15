package org.oswfm.kafkaserviceclient.config;

import org.oswfm.kafkaserviceclient.service.KafkaPublisherService;
import org.oswfm.kafkaserviceclient.service.TopicManagementService;
import org.oswfm.kafkaserviceclient.service.TopicSubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@AutoConfiguration
@EnableConfigurationProperties(KafkaClientProperties.class)
public class KafkaClientAutoConfiguration {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String springBootstrapServers;

    private String resolveBootstrapServers(KafkaClientProperties props) {
        String override = props.getBootstrapServers();
        return (override != null && !override.isBlank()) ? override : springBootstrapServers;
    }

    @Bean
    @ConditionalOnMissingBean(AdminClient.class)
    public AdminClient kafkaAdminClient(KafkaClientProperties props) {
        return AdminClient.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, resolveBootstrapServers(props),
                AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, String.valueOf(props.getAdminRequestTimeoutMs()),
                AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, String.valueOf(props.getAdminRequestTimeoutMs() + 5000)
        ));
    }

    @Bean
    @ConditionalOnMissingBean(name = "kafkaClientProducerFactory")
    public ProducerFactory<String, String> kafkaClientProducerFactory(KafkaClientProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, resolveBootstrapServers(props));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, 3);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<String, String> kafkaTemplate(KafkaClientProperties props) {
        return new KafkaTemplate<>(kafkaClientProducerFactory(props));
    }

    @Bean
    @ConditionalOnMissingBean(ConcurrentKafkaListenerContainerFactory.class)
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            KafkaClientProperties props) {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, resolveBootstrapServers(props));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, props.getAutoOffsetReset());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        ConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(config);
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(factory);
        return containerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(TopicManagementService.class)
    public TopicManagementService topicManagementService(AdminClient kafkaAdminClient) {
        return new TopicManagementService(kafkaAdminClient);
    }

    @Bean
    @ConditionalOnMissingBean(KafkaPublisherService.class)
    public KafkaPublisherService kafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate,
                                                        ObjectMapper objectMapper) {
        return new KafkaPublisherService(kafkaTemplate, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(TopicSubscriptionService.class)
    public TopicSubscriptionService topicSubscriptionService(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        return new TopicSubscriptionService(kafkaListenerContainerFactory);
    }
}
