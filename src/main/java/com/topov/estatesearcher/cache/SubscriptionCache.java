package com.topov.estatesearcher.cache;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.state.subscription.update.SubscriptionUpdate;

import java.util.Optional;

public interface SubscriptionCache {
    void evictCache(String chatId);
    void modifySubscription(String chatId, SubscriptionUpdate subscriptionUpdate);
    Optional<Subscription> getCachedSubscription(String chatId);
    boolean flush(String chatId);
}
