package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Service holds all BotState implementation classes which are requested by BotUpdateHandler
 */
@Service
public class BotStateProviderImpl implements BotStateProvider {

    private final Map<BotState.StateName, AbstractBotState> botStates;

    @Autowired
    public BotStateProviderImpl(List<AbstractBotState> botStates) {
        this.botStates = botStates.stream()
            .collect(toMap(
                AbstractBotState::getStateName,
                Function.identity()
                )
            );
    }

    @Override
    public BotState getBotState(BotState.StateName state) {
        if (this.botStates.containsKey(state)) {
            return this.botStates.get(state);
        } else {
            throw new RuntimeException("State doesnt exist");
        }
    }
}
