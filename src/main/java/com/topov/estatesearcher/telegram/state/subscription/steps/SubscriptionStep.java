package com.topov.estatesearcher.telegram.state.subscription.steps;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionUpdator;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;

public interface SubscriptionStep {
    UpdateResult handleSubscriptionStep(Update update);
    List<KeyboardButton> getKeyboardButtons(Update update);
    Hint getHint();

    enum StepName {
        PRICE
    }
}
