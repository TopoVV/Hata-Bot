package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.BotResponse;
import com.topov.estatesearcher.telegram.Result;
import com.topov.estatesearcher.telegram.UserContext;
import lombok.Getter;

import java.util.Optional;

public class CommandResult implements Result {
    protected final Long chatId;
    protected final String message;

    public CommandResult(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotResponse createResponse() {
        return BotResponse.builder()
            .forUser(this.chatId)
            .reply(this.message)
            .build();
    }
}
