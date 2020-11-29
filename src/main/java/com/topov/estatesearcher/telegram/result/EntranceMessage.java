package com.topov.estatesearcher.telegram.result;

import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
