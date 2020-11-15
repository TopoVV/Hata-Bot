package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;

public interface SubscriptionStorage {
    void saveSubscription(long chatId, Subscription subscription);
    List<Subscription> getAllSubscriptions();
}
