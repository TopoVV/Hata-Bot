package com.topov.estatesearcher.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Log
public class RootConfig {

    @Bean
    TaskScheduler taskScheduler() {
        log.info("Instantiating task scheduler");
        return new ThreadPoolTaskScheduler();
    }
}
