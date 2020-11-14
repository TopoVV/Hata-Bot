package com.topov.estatesearcher.telegram.state;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractSubscriptionStep {
    private final int stepOrder;

    protected AbstractSubscriptionStep(int stepOrder) {
        this.stepOrder = stepOrder;
    }


    public abstract SubscriptionUpdateResult handleSubscriptionStep(Update update);
}
