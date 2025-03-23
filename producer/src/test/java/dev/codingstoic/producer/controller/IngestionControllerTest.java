package dev.codingstoic.producer.controller;

import dev.codingstoic.producer.model.account.IngestionType;
import dev.codingstoic.producer.service.IngestionProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IngestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IngestionProducer ingestionProducer;

    @InjectMocks
    private IngestionController ingestionController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingestionController).build();
    }


    @Test
    void postAccountForIngestion() throws Exception {

        mockMvc.perform(post("/api/v1/ingestion/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"12345\"}"))
                .andExpect(status().isOk());

        verify(ingestionProducer).sendIngestionEvent("ACCOUNT_INGESTION_V1", "12345", IngestionType.ACCOUNT);
    }

    @Test
    void postCustomerForIngestion() throws Exception {

        mockMvc.perform(post("/api/v1/ingestion/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerNumber\":\"67890\"}"))
                .andExpect(status().isOk());

        verify(ingestionProducer).sendIngestionEventWithHeaders("CUSTOMER_INGESTION_V1", "67890", IngestionType.CUSTOMER);
    }
}
