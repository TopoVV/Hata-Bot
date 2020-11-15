package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

public interface SubscriptionStorage {
    void saveSubscription(long chatId, Subscription subscription);
}
