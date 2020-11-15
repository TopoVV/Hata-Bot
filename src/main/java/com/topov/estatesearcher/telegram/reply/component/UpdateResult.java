package com.topov.estatesearcher.telegram.reply.component;

import lombok.Getter;

@Getter
public class UpdateResult {
    private final String message;

    public UpdateResult(String message) {
        this.message = message;
    }
}
