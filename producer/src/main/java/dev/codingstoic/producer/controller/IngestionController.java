package dev.codingstoic.producer.controller;

import dev.codingstoic.producer.model.account.IngestionType;
import dev.codingstoic.producer.models.AccountIngestionRequest;
import dev.codingstoic.producer.models.CustomerIngestionRequest;
import dev.codingstoic.producer.service.IngestionProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.codingstoic.producer.constant.Constants.ACCOUNT_INGESTION_TOPIC;
import static dev.codingstoic.producer.constant.Constants.CUSTOMER_INGESTION_TOPIC;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ingestion")
public class IngestionController {

    private IngestionProducer ingestionProducer;

    @PostMapping("/account")
    public ResponseEntity<Void> postAccountForIngestion(@RequestBody AccountIngestionRequest ingestionRequest) {
        log.info("IngestionController | postAccountForIngestion | requestBody {}", ingestionRequest);
        ingestionProducer.sendIngestionEvent(ACCOUNT_INGESTION_TOPIC, ingestionRequest.accountNumber(), IngestionType.ACCOUNT);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customer")
    public ResponseEntity<Void> postCustomerForIngestion(@RequestBody CustomerIngestionRequest ingestionRequest) {
        log.info("IngestionController | postCustomerForIngestion | requestBody | {}", ingestionRequest);
        ingestionProducer.sendIngestionEventWithHeaders(CUSTOMER_INGESTION_TOPIC, ingestionRequest.customerNumber(), IngestionType.CUSTOMER);
        return ResponseEntity.ok().build();
    }
}
