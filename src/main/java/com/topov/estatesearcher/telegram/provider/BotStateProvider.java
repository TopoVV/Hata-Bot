package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;

public interface BotStateProvider {
    BotState getBotState(BotStateName state);
}
