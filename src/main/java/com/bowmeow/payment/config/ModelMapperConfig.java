package com.bowmeow.payment.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // 이미 싱글톤으로 동작하고 있음
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
