package com.topov.estatesearcher.telegram.state.subscription;

public interface SubscriptionStorage {
    void saveSubscription(long chatId, Subscription subscription);
}
