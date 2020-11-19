package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandler;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandlerName;

import java.util.Optional;

public interface SubscriptionHandlerEvaluator {
    void setSubscriptionStepForUser(long chatId, SubscriptionHandlerName subscriptionUpdateHandlerNames);
    Optional<SubscriptionHandlerName> getCurrentSubscriptionHandlerForUser(long chatId);
}
