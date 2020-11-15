package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionStep;
import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionUpdator;

import java.util.Optional;

public interface SubscriptionCache {
    void removeCachedSubscription(long chatId);
    void modifySubscription(long chatId, SubscriptionUpdator subscriptionUpdator);
    Optional<Subscription> getCachedSubscription(Long chatId);
}
