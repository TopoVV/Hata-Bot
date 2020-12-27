package com.topov.hatabot;

import com.topov.hatabot.telegram.HataBot;
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
@ComponentScan(basePackages = {"com.topov.hatabot"})
@EnableScheduling
@EnableAsync
public class HataBotApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("prod");
        context.register(HataBotApp.class);
        context.refresh();
        HataBot hataBot = context.getBean(HataBot.class);
        try {
            log.info("Registering telegram bot");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(hataBot);
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
