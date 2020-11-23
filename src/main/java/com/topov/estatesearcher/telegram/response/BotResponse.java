package com.topov.estatesearcher.telegram.response;

import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
public class BotResponse {
    private final String forUser;
    private final String reply;

    @Builder
    public BotResponse(Long forUser, String reply) {
        this.forUser = forUser.toString();
        this.reply = reply;
    }

    public SendMessage createTelegramMessage() {
        return new SendMessage(this.forUser, this.reply);
    }
}
