package com.topov.estatesearcher.telegram;

import lombok.Getter;

@Getter
public class UpdateResult implements Result {
    private final Long chatId;
    private final String message;

    public UpdateResult(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotResponse createResponse() {
        return BotResponse.builder()
            .forUser(this.chatId)
            .reply(message)
            .build();
    }
}
