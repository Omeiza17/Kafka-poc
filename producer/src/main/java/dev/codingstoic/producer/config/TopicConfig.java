package dev.codingstoic.producer.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static dev.codingstoic.producer.constant.Constants.ACCOUNT_INGESTION_V2_TOPIC;
import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG;

@Slf4j
@Configuration
public class TopicConfig {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${spring.kafka.properties.security.protocol}")
    private String protocol;
    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String mechanism;
    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        log.info("TopicConfig | values {} - {} - {} - {}", bootstrapAddress, protocol, mechanism, jaasConfig);
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put("security.protocol", protocol);
        config.put("sasl.mechanism", mechanism);
        config.put("sasl.jaas.config", jaasConfig);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic ingestionTopic() {
        return TopicBuilder.name(ACCOUNT_INGESTION_V2_TOPIC)
                .partitions(5)
                .config(RETENTION_MS_CONFIG, "86400000")
                .build();
    }
}
