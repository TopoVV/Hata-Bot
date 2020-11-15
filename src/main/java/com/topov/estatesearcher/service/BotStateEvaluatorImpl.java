package com.topov.estatesearcher.service;

import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BotStateEvaluatorImpl implements BotStateEvaluator {

    /**
     * The map stores currently correspondent state for each user.
     */
    private final Map<Long, BotState.StateName> userBotStates = new HashMap<>();

    @Override
    public void setStateForUser(long chatId, BotState.StateName state) {
        this.userBotStates.put(chatId, state);
    }

    @Override
    public BotState.StateName getStateForUser(long chatId) {
        return userBotStates.get(chatId);
    }
}
