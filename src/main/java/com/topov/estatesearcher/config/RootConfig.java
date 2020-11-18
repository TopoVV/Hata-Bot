package com.topov.estatesearcher.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Log4j2
@Configuration
@EnableScheduling
public class RootConfig {

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        log.info("Instantiating property sources placeholder scheduler");
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    TaskScheduler taskScheduler() {
        log.info("Instantiating the task scheduler");
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    MessageSource messageSource() {
        log.info("Instantiating the message source");
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages/replies", "messages/commands");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
