package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Telegram Bot can serve multiple users simultaneously and the update processing logic depends on current bot state
 * for each user. This class stores the {@link BotState.StateName} enum value and the correspondent user.
 */

@Service
public class BotStateEvaluatorImpl implements BotStateEvaluator {
    private final Map<Long, BotState.StateName> userBotStates = new HashMap<>();

    @Override
    public void setStateForUser(long chatId, BotState.StateName state) {
        this.userBotStates.put(chatId, state);
    }

    @Override
    public Optional<BotState.StateName> getUserCurrentStateName(long chatId) {
        return Optional.ofNullable(userBotStates.get(chatId));
    }
}
