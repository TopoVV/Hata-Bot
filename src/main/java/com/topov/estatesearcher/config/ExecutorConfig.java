package com.topov.estatesearcher.config;

import com.topov.estatesearcher.postprocessor.CommandMappingAnnotationBeanPostProcessor;
import com.topov.estatesearcher.postprocessor.KeyboardDescriptionAnnotationPostProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Log4j2
@Configuration
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        log.info("Instantiating property sources placeholder scheduler");
        return new PropertySourcesPlaceholderConfigurer();
    }

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

    @Bean
    CommandMappingAnnotationBeanPostProcessor telegramBotStateAnnotationBeanPostProcessor() {
        return new CommandMappingAnnotationBeanPostProcessor();
    }

    @Bean
    KeyboardDescriptionAnnotationPostProcessor keyboardDescriptionAnnotationPostProcessor() {
        return new KeyboardDescriptionAnnotationPostProcessor();
    }
}
