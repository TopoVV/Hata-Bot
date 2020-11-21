package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.TelegramReplyAssembler;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@Service
public class EstateBot extends TelegramLongPollingBot {
    private final BotUpdateProcessor updateHandler;
    private final TelegramReplyAssembler replyAssembler;
    private final BotStateEvaluator stateEvaluator;

    private final String token;
    private final String username;

    @Autowired
    public EstateBot(BotUpdateProcessor updateHandler,
                     TelegramReplyAssembler replyAssembler,
                     BotStateEvaluator stateEvaluator,
                     @Value("${bot.token}") String token,
                     @Value("${bot.username}") String username) {
        this.updateHandler = updateHandler;
        this.replyAssembler = replyAssembler;
        this.stateEvaluator = stateEvaluator;
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
        final Long chatId = update.getMessage().getChatId();

        if (isFirstInteraction(chatId)) {
            doFirstInteraction(update);
        }

        final UpdateResult updateResult = this.updateHandler.processUpdate(update);
        final Keyboard keyboard = this.updateHandler.getKeyboard(update);

        final SendMessage reply = this.replyAssembler.assembleReply(updateResult, keyboard, chatId);

        executeApiAction(reply);
    }

    private void executeApiAction(BotApiMethod<?> action) {
        try {
            execute(action);
        } catch (TelegramApiException e) {
            log.error("Telegram API exception", e);
        }
    }

    private void doFirstInteraction(Update update) {
        final long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        if (text.equals("/start")) {
            this.updateHandler.processFirstInteraction(chatId);
            executeApiAction(new SendMessage(String.valueOf(chatId), "Welcome"));
        } else {
            throw new RuntimeException("Unknown user");
        }
    }

    public void sendNotification(Long chatId, Announcement announcement) {
        try {
            execute(new SendMessage(String.valueOf(chatId), announcement.toString()));
        } catch (TelegramApiException e) {
            log.error("Cannot send notification", e);
        }
    }

    private boolean isFirstInteraction(long chatId) {
        return this.stateEvaluator.isUserFirstInteraction(chatId);
    }
}
