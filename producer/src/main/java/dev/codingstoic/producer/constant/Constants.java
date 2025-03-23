package dev.codingstoic.producer.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String ACCOUNT_INGESTION_TOPIC = "ACCOUNT_INGESTION_V1";
    public static final String CUSTOMER_INGESTION_TOPIC = "CUSTOMER_INGESTION_V1";
}
