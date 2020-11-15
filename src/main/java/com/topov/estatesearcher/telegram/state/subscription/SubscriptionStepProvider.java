package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionStep;

import java.util.Optional;

public interface SubscriptionStepProvider {
    SubscriptionStep getSubscriptionStep(SubscriptionStep.StepName stepName);
    Optional<SubscriptionStep.StepName> getCurrentStepName(long chatId);
    void setSubscriptionStepForUser(long chatId, SubscriptionStep.StepName stepName);
    void resetSubscriptionStepForUser(long chatId);
}