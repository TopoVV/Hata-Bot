package com.topov.estatesearcher.config;

import com.topov.estatesearcher.telegram.EstateBot;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Log
@Configuration
@PropertySource(value = "classpath:bot.properties")
public class TelegramConfig {
    @Bean
    EstateBot estateBot() {
        log.info("Instantiating EstateBot");
        return new EstateBot();
    }
}
