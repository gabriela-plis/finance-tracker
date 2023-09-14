package com.financetracker.app.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static java.time.Clock.*;

@Configuration
public class AppConfig {

    @Bean
    Clock clock() {
        return systemUTC();
    }

}
