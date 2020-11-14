package com.topov.estatesearcher.telegram;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
public class UpdateResult {
    private final String message;

    public UpdateResult(String message) {
        this.message = message;
    }
}
