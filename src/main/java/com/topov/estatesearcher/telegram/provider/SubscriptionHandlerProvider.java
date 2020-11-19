package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandler;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandlerName;

import java.util.Optional;

public interface SubscriptionHandlerProvider {
    SubscriptionHandler getSubscriptionStep(SubscriptionHandlerName subscriptionHandlerName);
}
