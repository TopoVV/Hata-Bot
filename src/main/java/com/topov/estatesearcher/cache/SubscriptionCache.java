package com.topov.estatesearcher.cache;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.state.subscription.update.SubscriptionUpdate;

import java.util.Optional;

public interface SubscriptionCache {
    void evictCache(long chatId);
    void modifySubscription(long chatId, SubscriptionUpdate subscriptionUpdate);
    Optional<Subscription> getCachedSubscription(Long chatId);
    boolean flush(Long chatId);
}
