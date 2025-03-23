package dev.codingstoic.producer.service;

import dev.codingstoic.producer.model.account.IngestionEvent;
import dev.codingstoic.producer.model.account.IngestionType;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IngestionProducerTest {

    @Mock
    private KafkaTemplate<String, IngestionEvent> kafkaTemplate;

    @InjectMocks
    private IngestionProducer ingestionProducer;

    private String topic;
    private String message;

    @BeforeEach
    void setUp() {
        topic = "test-topic";
        message = "test-message";
    }

    @Test
    void sendIngestionEvent() {
        IngestionType type = IngestionType.ACCOUNT;
        ingestionProducer.sendIngestionEvent(topic, message, type);

        ArgumentCaptor<ProducerRecord<String, IngestionEvent>> recordCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(kafkaTemplate).send(recordCaptor.capture());

        ProducerRecord<String, IngestionEvent> record = recordCaptor.getValue();
        assertEquals(topic, record.topic());
        assertNotNull(record.key()); // UUID
        assertEquals(message, record.value().getData());
        assertEquals(type, record.value().getIngestionType());

    }

    @Test
    void sendIngestionEventWithHeaders() {
        IngestionType type = IngestionType.CUSTOMER;

        ingestionProducer.sendIngestionEventWithHeaders(topic, message, type);

        ArgumentCaptor<ProducerRecord<String, IngestionEvent>> recordCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(kafkaTemplate).send(recordCaptor.capture());

        ProducerRecord<String, IngestionEvent> record = recordCaptor.getValue();
        assertEquals(topic, record.topic());
        assertNotNull(record.key()); // UUID
        assertEquals(message, record.value().getData());
        assertEquals(type, record.value().getIngestionType());
        assertNotNull(record.headers().lastHeader("event-source"));
        assertEquals("producer-service", new String(record.headers().lastHeader("event-source").value()));
    }
}
