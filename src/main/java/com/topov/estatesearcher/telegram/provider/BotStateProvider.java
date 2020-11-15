package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.BotState;

public interface BotStateProvider {
    BotState getBotState(BotState.StateName state);
}
