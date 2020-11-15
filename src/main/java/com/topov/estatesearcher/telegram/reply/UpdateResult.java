package com.topov.estatesearcher.telegram.reply;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
public class UpdateResult {
    private final String message;

    public UpdateResult(String message) {
        this.message = message;
    }
}
