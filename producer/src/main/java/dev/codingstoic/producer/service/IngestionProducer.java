package dev.codingstoic.producer.service;

import dev.codingstoic.producer.model.account.IngestionEvent;
import dev.codingstoic.producer.model.account.IngestionType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class IngestionProducer {
    private KafkaTemplate<String, IngestionEvent> kafkaTemplate;

    public void sendIngestionEvent(String topic, String message, IngestionType ingestionType) {
        ProducerRecord<String, IngestionEvent> record = getIngestionEventProducerRecord(topic, message, ingestionType);
        kafkaTemplate.send(record);
    }

    public void sendIngestionEventWithHeaders(String topic, String message, IngestionType ingestionType) {
        ProducerRecord<String, IngestionEvent> record = getIngestionEventProducerRecord(topic, message, ingestionType);
        record.headers().add("event-source", "producer-service".getBytes(StandardCharsets.UTF_8));
        kafkaTemplate.send(record);
    }

    private static ProducerRecord<String, IngestionEvent> getIngestionEventProducerRecord(String topic, String message, IngestionType ingestionType) {
        String uniqueId = UUID.randomUUID().toString();
        IngestionEvent ingestionEvent = IngestionEvent.newBuilder()
                .setId(uniqueId)
                .setData(message)
                .setIngestionType(ingestionType)
                .build();
        return new ProducerRecord<>(topic, uniqueId, ingestionEvent);
    }
}
