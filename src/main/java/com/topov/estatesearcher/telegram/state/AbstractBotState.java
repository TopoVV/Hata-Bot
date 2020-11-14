package com.topov.estatesearcher.telegram.state;

import lombok.Getter;

@Getter
public abstract class AbstractBotState implements BotState {
    private final StateName stateName;

    protected AbstractBotState(StateName stateName) {
        this.stateName = stateName;
    }
}
