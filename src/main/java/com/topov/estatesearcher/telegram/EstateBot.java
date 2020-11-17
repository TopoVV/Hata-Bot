package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.telegram.reply.TelegramReplyAssembler;
import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Log4j2
@Service
public class EstateBot extends TelegramLongPollingBot {
    private final BotUpdateHandler updateHandler;
    private final TelegramReplyAssembler replyAssembler;

    private final String token;
    private final String username;

    @Autowired
    public EstateBot(BotUpdateHandler updateHandler,
                     TelegramReplyAssembler replyAssembler,
                     @Value("${bot.token}") String token,
                     @Value("${bot.username}") String username) {
        this.updateHandler = updateHandler;
        this.replyAssembler = replyAssembler;
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

        try {
            final UpdateResult updateResult = this.updateHandler.handleUpdate(update);
            final Hint hint = this.updateHandler.getHint(update);
            final Keyboard keyboard = this.updateHandler.getKeyboard(update);

            final SendMessage reply = this.replyAssembler.assembleReply(updateResult, keyboard, hint, chatId);

            execute(reply);
        } catch (TelegramApiException e) {
            log.error("Telegram API exception", e);
        } catch (RuntimeException e) {
            log.error("Error during processing the update ", e);
        }
    }

    public void sendNotification(Long chatId, Announcement announcement) {
        try {
            execute(new SendMessage(String.valueOf(chatId), announcement.toString()));
        } catch (TelegramApiException e) {
            log.error("Cannot send notification", e);
        }
    }
}
