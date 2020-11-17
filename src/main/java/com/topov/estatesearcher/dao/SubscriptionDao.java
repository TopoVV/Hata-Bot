package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Subscription;

import java.util.List;

public interface SubscriptionDao {
    void saveSubscription(long chatId, Subscription subscription);
    List<Subscription> getAllSubscriptions();
}
