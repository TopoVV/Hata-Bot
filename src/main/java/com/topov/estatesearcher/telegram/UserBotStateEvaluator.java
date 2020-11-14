package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotState;

public interface UserBotStateEvaluator {
    void setStateForUser(long chatId, BotState.StateName state);
    BotState.StateName getStateForUser(long chatId);
}
