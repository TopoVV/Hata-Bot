package com.topov.estatesearcher.telegram;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.notification.Notification;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@Service
@PropertySource(value = "classpath:bot.properties")
public class EstateBot extends TelegramLongPollingBot {
    private final BotUpdateProcessor updateProcessor;

    private final String token;
    private final String username;

    @Autowired
    public EstateBot(BotUpdateProcessor updateProcessor,
                     @Value("${bot.token}") String token,
                     @Value("${bot.username}") String username) {
        this.updateProcessor = updateProcessor;
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receiving update: {}", update);
        final UpdateWrapper updateWrapper = new UpdateWrapper(update);

        final SendInvoice sendInvoice = new SendInvoice(Integer.valueOf(
            update.getMessage().getChatId().toString()),
            "Invoice",
            "Invoice",
            "Payload",
            "1234",
            "321",
            "UAH",
            Lists.newArrayList(new LabeledPrice("lll", 30))
        );
        executeApiAction(sendInvoice);
        this.updateProcessor.processUpdate(updateWrapper).ifPresent(response -> {
            executeApiAction(response.createTelegramMessage());
        });

        this.updateProcessor.getEntranceMessage(updateWrapper).ifPresent(entranceMessage -> {
            executeApiAction(entranceMessage.createTelegramMessage());
        });

    }

    private void executeApiAction(BotApiMethod<?> action) {
        try {
            execute(action);
        } catch (TelegramApiException e) {
            log.error("Telegram API exception", e);
        }
    }

    public void sendNotification(Notification notification) {
        try {
            execute(new SendMessage(notification.getUserId(), notification.getText()));
        } catch (TelegramApiException e) {
            log.error("Cannot send notification", e);
        }
    }
}
