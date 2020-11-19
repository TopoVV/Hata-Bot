package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Telegram Bot can serve multiple users simultaneously and the update processing logic depends on current bot state
 * for each user. This class stores the {@link BotStateName} enum value and the correspondent user.
 */

@Service
public class BotStateEvaluatorImpl implements BotStateEvaluator {
    private final Map<Long, BotStateName> userBotStates = new HashMap<>();

    @Override
    public void setStateForUser(long chatId, BotStateName state) {
        this.userBotStates.put(chatId, state);
    }

    @Override
    public Optional<BotStateName> getUserCurrentStateName(long chatId) {
        return Optional.ofNullable(userBotStates.get(chatId));
    }
}
