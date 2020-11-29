package com.topov.hatabot.service;

import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.model.SubscriptionList;

import java.util.Optional;

public interface SubscriptionService {
    void saveSubscription(Subscription subscription);
    SubscriptionList getUserSubscriptions(String userId);
    Optional<Subscription> findSubscription(long subscriptionId, String userId);
    void removeSubscription(Long subscriptionId);
}
