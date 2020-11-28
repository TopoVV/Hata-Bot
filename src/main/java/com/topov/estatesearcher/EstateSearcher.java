package com.topov.estatesearcher;

import com.topov.estatesearcher.telegram.EstateBot;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Log4j2
@Configuration
@ComponentScan(basePackages = { "com.topov.estatesearcher" })
@EnableScheduling
@EnableAsync
public class EstateSearcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(EstateSearcher.class);
        context.refresh();

        EstateBot estateBot = context.getBean(EstateBot.class);
        try {
            log.info("Registering telegram bot");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(estateBot);
        } catch (TelegramApiException e) {
            log.error("Error when instantiating EstateBot", e);
            throw new RuntimeException("Cannot instantiate bot");
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        log.info("Instantiating property sources placeholder scheduler");
        return new PropertySourcesPlaceholderConfigurer();
    }
}
