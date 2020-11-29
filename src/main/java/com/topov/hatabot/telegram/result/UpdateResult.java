package com.topov.hatabot.telegram.result;

import com.topov.hatabot.telegram.response.BotResponse;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UpdateResult {
    private final String message;

    public static UpdateResult withMessage(String message) {
        return new UpdateResult(message);
    }

    public UpdateResult(String message) {
        this.message = message;
    }

    public Optional<BotResponse> createResponse(String chatId) {
        final BotResponse response = BotResponse.builder()
            .chatId(chatId)
            .reply(message)
            .build();

        return Optional.of(response);
    }
}
