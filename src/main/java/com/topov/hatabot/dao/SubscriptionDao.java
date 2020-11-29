package com.topov.hatabot.dao;

import com.topov.hatabot.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao {
    void saveSubscription(Subscription subscription);
    List<Subscription> getAllUserSubscriptions(String userId);
    List<Subscription> getAllSubscriptions();
    Optional<Subscription> findSubscription(long subscriptionId, String userId);
    void deleteSubscription(Long subscriptionId);
}
