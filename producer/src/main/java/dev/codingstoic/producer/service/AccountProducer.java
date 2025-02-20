package dev.codingstoic.producer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        log.info("Sending account message {} to topic {}", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
