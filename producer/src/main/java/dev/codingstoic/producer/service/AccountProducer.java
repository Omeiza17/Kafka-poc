package dev.codingstoic.producer.service;

import dev.codingstoic.producer.model.account.IngestionEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.codingstoic.producer.constant.Constants.ACCOUNT;

@Slf4j
@Service
@AllArgsConstructor
public class AccountProducer {
    private KafkaTemplate<String, IngestionEvent> kafkaTemplate;

    public void send(String topic, String message) {
        var ingestionEvent = new IngestionEvent();
        String uniqueId = UUID.randomUUID().toString();
        ingestionEvent.setId(uniqueId);
        ingestionEvent.setData(message);
        ingestionEvent.setDataType(ACCOUNT);

        log.info("Sending {} to topic {}", ingestionEvent, topic);
        kafkaTemplate.send(topic, ingestionEvent);
    }
}
