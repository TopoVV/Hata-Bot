package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    void saveSubscription(Subscription subscription);
    List<Subscription> getAllSubscriptionsForUser(String chatId);
    Optional<Subscription> findSubscription(long subscriptionId, String chatId);
    void removeSubscription(Long subscriptionId);
    String getUserSubscriptionsInfo(String chatId);
}
