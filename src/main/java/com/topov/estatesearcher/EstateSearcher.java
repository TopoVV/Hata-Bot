package com.topov.estatesearcher;

import com.topov.estatesearcher.config.RootConfig;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.telegram.EstateBot;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Log4j2
public class EstateSearcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RootConfig.class);
        context.refresh();

        EstateBot estateBot = context.getBean(EstateBot.class);
        try {
            log.info("Refistering telegram bot");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(estateBot);
        } catch (TelegramApiException e) {
            log.error("Error when instantiating EstateBot", e);
            throw new RuntimeException("Cannot instantiate bot");
        }
    }

}
