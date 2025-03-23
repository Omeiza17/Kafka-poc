package dev.codingstoic.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import dev.codingstoic.producer.model.account.IngestionEvent;

import static dev.codingstoic.consumer.constant.Constants.ACCOUNT_INGESTION_V2_TOPIC;

@Slf4j
@Service
public class AccountConsumer {

    @KafkaListener(topics = ACCOUNT_INGESTION_V2_TOPIC)
    public void consume(IngestionEvent event) {
        log.info("Received ingestion event: {}", event);
    }
}
