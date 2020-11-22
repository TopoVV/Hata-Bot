package com.topov.estatesearcher.telegram;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BotResponse {
    private final String forUser;
    private final String message;

    @Builder
    public BotResponse(Long forUser, String stateMessage, String reply) {
        this.forUser = forUser.toString();
        this.message = String.format("%s\n\n%s", stateMessage, reply);
    }
}
