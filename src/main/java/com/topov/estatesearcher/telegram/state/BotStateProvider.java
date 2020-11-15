package com.topov.estatesearcher.telegram.state;

public interface BotStateProvider {
    BotState getBotState(BotState.StateName state);
}
