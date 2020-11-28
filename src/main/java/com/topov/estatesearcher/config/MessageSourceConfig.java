package com.topov.estatesearcher.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Log4j2
@Configuration
@PropertySource(value = "classpath:bot.properties")
public class TelegramConfig {

}
