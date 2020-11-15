package com.topov.estatesearcher.telegram.state;

public interface UserBotStateEvaluator {
    void setStateForUser(long chatId, BotState.StateName state);
    BotState.StateName getStateForUser(long chatId);
}
