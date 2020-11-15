package com.topov.estatesearcher.telegram.reply;

import lombok.Getter;

@Getter
public class UpdateResult {
    private final String message;

    public UpdateResult(String message) {
        this.message = message;
    }
}
