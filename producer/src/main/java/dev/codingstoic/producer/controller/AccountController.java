package dev.codingstoic.producer.controller;


import dev.codingstoic.producer.models.AccountIngestionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-ingestion")
public class AccountController {

    @PostMapping
    public ResponseEntity<Void> postAccountForIngestion(@RequestBody AccountIngestionRequest ingestionRequest) {
        log.info("Received request for ingestion request {}", ingestionRequest);
        return ResponseEntity.ok().build();
    }
}
