package com.topov.hatabot.service;

import com.topov.hatabot.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    void saveSubscription(Subscription subscription);
    List<Subscription> getUserSubscriptions(String userId);
    Optional<Subscription> findSubscription(long subscriptionId, String userId);
    void removeSubscription(Long subscriptionId);
}
