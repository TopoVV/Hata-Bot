package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.TelegramReplyAssembler;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class EstateBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String username;

    private final BotUpdateHandler updateHandler;
    private final TelegramReplyAssembler replyAssembler;

    @Autowired
    public EstateBot(BotUpdateHandler updateHandler, TelegramReplyAssembler replyAssembler) {
        this.updateHandler = updateHandler;
        this.replyAssembler = replyAssembler;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void onUpdateReceived(Update update) {
        final Long chatId = update.getMessage().getChatId();

        try {
            final UpdateResult updateResult = this.updateHandler.handleUpdate(update);
            final Hint hint = this.updateHandler.getHint(update);
            final Keyboard keyboard = this.updateHandler.getKeyboard(update);

            final SendMessage reply = replyAssembler.assembleReply(updateResult, hint, keyboard, chatId);

            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
