package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;

import java.util.Optional;

public interface BotStateEvaluator {
    void setStateForUser(long chatId, BotStateName state);
    Optional<BotStateName> getUserCurrentStateName(long chatId);
}
