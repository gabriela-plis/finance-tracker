package com.financetracker.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static java.time.Clock.systemUTC;

@Configuration
public class AppConfig {

    @Bean
    Clock clock() {
        return systemUTC();
    }
}
