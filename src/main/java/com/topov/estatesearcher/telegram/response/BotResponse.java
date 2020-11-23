package com.topov.estatesearcher.telegram.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BotResponse {
    private final String forUser;
    private final String reply;

    @Builder
    public BotResponse(Long forUser, String reply) {
        this.forUser = forUser.toString();
        this.reply = reply;
    }

    @Override
    public String toString() {
        return String.format("%s", this.reply);
    }
}