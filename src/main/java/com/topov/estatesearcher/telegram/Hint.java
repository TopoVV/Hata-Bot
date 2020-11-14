package com.topov.estatesearcher.telegram;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
public class Hint {
    private final String message;

    public Hint(String message) {
        this.message =  message;
    }
}
