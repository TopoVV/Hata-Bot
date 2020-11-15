package com.topov.estatesearcher.service;

import com.topov.estatesearcher.telegram.state.BotState;

public interface BotStateEvaluator {
    void setStateForUser(long chatId, BotState.StateName state);
    BotState.StateName getStateForUser(long chatId);
}
