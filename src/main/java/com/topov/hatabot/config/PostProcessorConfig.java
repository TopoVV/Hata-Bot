package com.topov.hatabot.config;

import com.topov.hatabot.postprocessor.CommandMappingAnnotationBPP;
import com.topov.hatabot.postprocessor.KeyboardDescriptionAnnotationBPP;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class PostProcessorConfig {
    @Bean
    CommandMappingAnnotationBPP telegramBotStateAnnotationBeanPostProcessor() {
        log.info("Instantiating the CommandMappingAnnotationBeanPostProcessor");
        return new CommandMappingAnnotationBPP();
    }

    @Bean
    KeyboardDescriptionAnnotationBPP keyboardDescriptionAnnotationPostProcessor() {
        log.info("Instantiating the KeyboardDescriptionAnnotationPostProcessor");
        return new KeyboardDescriptionAnnotationBPP();
    }
}
