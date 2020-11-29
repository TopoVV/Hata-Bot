package com.topov.hatabot.telegram.state;

import lombok.Getter;

@Getter
public final class StateProperties {
    private final BotStateName stateName;
    private final String headerKey;
    private final String commandsKey;

    public StateProperties(BotStateName stateName, String headerKey, String commandsKey) {
        this.stateName = stateName;
        this.headerKey = headerKey;
        this.commandsKey = commandsKey;
    }
}
