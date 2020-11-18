package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    void saveSubscription(long chatId, Subscription subscription);
    List<Subscription> getAllSubscriptionsForUser(long chatId);
}
