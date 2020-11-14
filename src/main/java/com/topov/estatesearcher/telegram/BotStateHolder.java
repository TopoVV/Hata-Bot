package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotState;

public interface BotStateHolder {
    void setStateForUser(long chatId, BotState state);
    BotState getStateForUser(long chatId);
}
