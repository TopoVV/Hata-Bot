package com.topov.estatesearcher.config;

import com.topov.estatesearcher.postprocessor.CommandMappingAnnotationBeanPostProcessor;
import com.topov.estatesearcher.postprocessor.KeyboardDescriptionAnnotationPostProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class PostProcessorConfig {
    @Bean
    CommandMappingAnnotationBeanPostProcessor telegramBotStateAnnotationBeanPostProcessor() {
        log.info("Instantiating the CommandMappingAnnotationBeanPostProcessor");
        return new CommandMappingAnnotationBeanPostProcessor();
    }

    @Bean
    KeyboardDescriptionAnnotationPostProcessor keyboardDescriptionAnnotationPostProcessor() {
        log.info("Instantiating the KeyboardDescriptionAnnotationPostProcessor");
        return new KeyboardDescriptionAnnotationPostProcessor();
    }
}
