package dev.codingstoic.producer.controller;


import dev.codingstoic.producer.models.AccountIngestionRequest;
import dev.codingstoic.producer.service.AccountProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.codingstoic.producer.constant.Constants.ACCOUNT_INGESTION_V1_TOPIC;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account-ingestion")
public class AccountController {

    private AccountProducer accountProducer;

    @PostMapping
    public ResponseEntity<Void> postAccountForIngestion(@RequestBody AccountIngestionRequest ingestionRequest) {
        log.info("Received request for ingestion request {}", ingestionRequest);
        accountProducer.send(ACCOUNT_INGESTION_V1_TOPIC, ingestionRequest.accountNumber());
        return ResponseEntity.ok().build();
    }
}
