package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    void saveSubscription(Subscription subscription);
    List<Subscription> getAllSubscriptionsForUser(long chatId);
    Optional<Subscription> findSubscription(long subscriptionId, long chatId);
    void removeSubscription(Long subscriptionId);
}
