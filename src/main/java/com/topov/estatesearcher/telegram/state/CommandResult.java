package com.topov.estatesearcher.telegram.state;

import lombok.Getter;

import java.util.Optional;

public class CommandResult {
    @Getter
    private final String message;

    private BotStateName newState;

    public CommandResult(String message) {
        this.message = message;
    }

    public CommandResult(BotStateName newState, String message) {
        this.newState = newState;
        this.message = message;
    }

    public Optional<BotStateName> changedState() {
        return Optional.ofNullable(newState);
    }
}
