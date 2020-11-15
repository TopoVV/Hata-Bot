package com.topov.estatesearcher.telegram.reply;

import lombok.Getter;

public class Hint {
    private final StringBuilder message = new StringBuilder();

    public Hint(String message) {
        this.message.append(message);
    }

    public void appendMessage(String message) {
        this.message.append(message);
    }

    public String getMessage() {
        return this.message.toString();
    }
}
