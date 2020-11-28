package com.topov.estatesearcher.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Log4j2
@Configuration
public class MessageSourceConfig {
    @Bean
    MessageSource messageSource() {
        log.info("Instantiating the message source");
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages");
        source.setCacheSeconds(3600);
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
