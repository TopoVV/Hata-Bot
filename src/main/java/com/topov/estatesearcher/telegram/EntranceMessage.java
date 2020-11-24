package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

public class EntranceMessage {
    private final Keyboard keyboard;
    private final String entranceText;
    private final String chatId;

    public EntranceMessage(String chatId, String entranceText, Keyboard keyboard) {
        this.keyboard = keyboard;
        this.entranceText = entranceText;
        this.chatId = chatId;
    }

    public SendMessage createTelegramMessage() {
        final SendMessage message = new SendMessage(this.chatId, this.entranceText);
        message.setReplyMarkup(this.keyboard.createKeyboardMarkup());
        return message;
    }
}
