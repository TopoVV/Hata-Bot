package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.BotState;

import java.util.Optional;

public interface BotStateProvider {
    BotState getBotState(BotState.StateName state);
}
