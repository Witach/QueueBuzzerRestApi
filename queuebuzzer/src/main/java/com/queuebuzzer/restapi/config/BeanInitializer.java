package com.queuebuzzer.restapi.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class BeanInitializer {
    @Bean
    public Faker faker() {
        return new Faker(new Locale("pl", "PL"));
    }
}
