package com.topov.estatesearcher.telegram.state.subscription.steps;

import lombok.Getter;

@Getter
public abstract class AbstractSubscriptionStep implements SubscriptionStep {
    private final StepName stepName;

    public AbstractSubscriptionStep(StepName stepName) {
        this.stepName = stepName;
    }
}
