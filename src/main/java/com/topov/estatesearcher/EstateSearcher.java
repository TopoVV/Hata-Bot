package com.topov.estatesearcher;

import com.topov.estatesearcher.config.RootConfig;
import com.topov.estatesearcher.telegram.EstateBot;
import lombok.extern.java.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Log
public class EstateSearcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RootConfig.class);
        context.refresh();

        EstateBot estateBot = context.getBean(EstateBot.class);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(estateBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.info("Error when instantiating EstateBot");
            throw new RuntimeException("Cannot instantiate bot");
        }
    }
}
