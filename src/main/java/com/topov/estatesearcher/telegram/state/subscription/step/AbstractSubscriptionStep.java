package com.topov.estatesearcher.telegram.state.subscription.step;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Collections;
import java.util.List;

@Getter
public abstract class AbstractSubscriptionStep implements SubscriptionStep {
    private final StepName stepName;

    public AbstractSubscriptionStep(StepName stepName) {
        this.stepName = stepName;
    }

    @Override
    public List<KeyboardButton> getKeyboardButtons(Update update) {
        return Collections.emptyList();
    }
}
