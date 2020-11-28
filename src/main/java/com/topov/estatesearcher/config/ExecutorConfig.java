package com.topov.estatesearcher.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Log4j2
@Configuration
public class ExecutorConfig {
    @Bean
    TaskScheduler taskScheduler() {
        log.info("Instantiating the task scheduler");
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    TaskExecutor taskExecutor() {
        log.info("Instantiating the task executor");
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(3);
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setThreadNamePrefix("task-executor-1");
        taskExecutor.setQueueCapacity(100);
        return taskExecutor;
    }

}
