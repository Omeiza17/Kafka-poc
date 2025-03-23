package dev.codingstoic.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TopicConfigTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private TopicConfig topicConfig;

    @Test
    public void testKafkaAdmin() {
        KafkaAdmin kafkaAdmin = topicConfig.kafkaAdmin();
        assertNotNull(kafkaAdmin);
    }

    @Test
    public void testIngestionTopic() {
        NewTopic topic = topicConfig.ingestionTopic();
        assertEquals("ACCOUNT_INGESTION_V1", topic.name());
        assertEquals(5, topic.numPartitions());
        assertEquals("86400000", topic.configs().get("retention.ms"));
    }

    @Test
    public void testCustomerIngestionTopic() {
        NewTopic topic = topicConfig.customerIngestionTopic();
        assertEquals("CUSTOMER_INGESTION_V1", topic.name());
        assertEquals(3, topic.numPartitions());
        assertEquals("86400000", topic.configs().get("retention.ms"));
    }

}
