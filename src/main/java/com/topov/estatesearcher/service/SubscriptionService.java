package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    void saveSubscription(Subscription subscription);
    SubscriptionList getUserSubscriptions(String userId);
    Optional<Subscription> findSubscription(long subscriptionId, String userId);
    void removeSubscription(Long subscriptionId);
}
