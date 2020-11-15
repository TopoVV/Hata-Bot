package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractBotState implements BotState {
    private final StateName stateName;

    protected final BotStateEvaluator stateEvaluator;

    protected AbstractBotState(StateName stateName, BotStateEvaluator stateEvaluator) {
        this.stateName = stateName;
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }
}
