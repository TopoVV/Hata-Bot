package com.topov.estatesearcher.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Log
@Configuration
@ComponentScan(basePackages = { "com.topov.estatesearcher.*" })
public class RootConfig {

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        log.info("Instantiating property sources placeholder scheduler");
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    TaskScheduler taskScheduler() {
        log.info("Instantiating task scheduler");
        return new ThreadPoolTaskScheduler();
    }
}
