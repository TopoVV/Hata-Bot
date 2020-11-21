package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.BotStateName;

import java.util.Optional;

public interface BotStateEvaluator {
    void setStateForUser(long chatId, BotStateName state);
    BotStateName getUserCurrentStateName(long chatId);
    boolean isUserFirstInteraction(long chatId);
}
