package com.topov.estatesearcher;

import com.topov.estatesearcher.telegram.EstateBot;
import com.topov.estatesearcher.telegram.state.initial.TelegramBotStateAnnotationBeanPostProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Log4j2
@Configuration
@ComponentScan(basePackages = { "com.topov.estatesearcher" })
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
    TelegramBotStateAnnotationBeanPostProcessor telegramBotStateAnnotationBeanPostProcessor() {
        return new TelegramBotStateAnnotationBeanPostProcessor();
    }

}
