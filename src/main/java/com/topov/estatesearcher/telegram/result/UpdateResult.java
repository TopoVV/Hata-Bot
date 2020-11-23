package com.topov.estatesearcher.telegram.result;

import com.topov.estatesearcher.telegram.response.BotResponse;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UpdateResult {
    private final String message;

    public UpdateResult(String message) {
        this.message = message;
    }

    public Optional<BotResponse> createResponse(Long chatId) {
        final BotResponse response = BotResponse.builder()
            .forUser(chatId)
            .reply(message)
            .build();

        return Optional.of(response);
    }
}
