package com.bowmeow.payment.config;

import com.bowmeow.payment.property.ImportProperties;
import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {
    private final ImportProperties importProperties;

    @Autowired
    public IamportConfig(ImportProperties importProperties) {
        this.importProperties = importProperties;
    }

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(importProperties.getKey(), importProperties.getSecret());
    }
}
