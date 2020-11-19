package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractBotState implements BotState {
    protected BotStateEvaluator stateEvaluator;

    private final StateName stateName;

    protected AbstractBotState(StateName stateName) {
        this.stateName = stateName;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }
}
