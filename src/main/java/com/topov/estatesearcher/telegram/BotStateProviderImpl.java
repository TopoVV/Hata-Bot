package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

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
        if (botStates.containsKey(state)) {
            return botStates.get(state);
        } else {
            throw new RuntimeException("Invalid state");
        }
    }
}
