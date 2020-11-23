package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao {
    void saveSubscription(Subscription subscription);
    List<Subscription> getAllSubscriptions();
    Optional<Subscription> findSubscription(long subscriptionId, String chatId);
    void deleteSubscription(Long subscriptionId);
}
