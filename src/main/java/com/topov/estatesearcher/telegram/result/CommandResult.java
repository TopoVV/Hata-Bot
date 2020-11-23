package com.topov.estatesearcher.telegram.result;

import com.topov.estatesearcher.telegram.response.BotResponse;
import org.checkerframework.checker.nullness.Opt;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;
import java.util.function.Consumer;

public class CommandResult {
    private final String message;
    private final boolean isEmpty;

    public static CommandResult withMessage( String message) {
        return new CommandResult(message);
    }

    public static CommandResult empty() {
        return new CommandResult();
    }

    public CommandResult(String message) {
        this.message = message;
        this.isEmpty = false;
    }

    public CommandResult() {
        this.message = "";
        this.isEmpty = true;
    }

    public Optional<BotResponse> createResponse(Long chatId) {
        if (!this.isEmpty) {
            final BotResponse response = BotResponse.builder()
                .forUser(chatId)
                .reply(this.message)
                .build();

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
}
