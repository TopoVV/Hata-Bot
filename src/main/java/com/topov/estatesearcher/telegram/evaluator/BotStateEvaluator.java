package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.BotState;

import java.util.Optional;

public interface BotStateEvaluator {
    void setStateForUser(long chatId, BotState.StateName state);
    Optional<BotState.StateName> getUserCurrentStateName(long chatId);
}
