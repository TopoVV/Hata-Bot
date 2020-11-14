package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BotStateHolderImpl implements BotStateHolder {

    /**
     * The map holds current bot state for each bot user.
     */
    private final Map<Long, BotState> chatBotStates = new HashMap<>();


    @Override
    public void setStateForUser(long chatId, BotState state) {
        this.chatBotStates.put(chatId, state);
    }

    @Override
    public BotState getStateForUser(long chatId) {
        return this.chatBotStates.get(chatId);
    }
}
