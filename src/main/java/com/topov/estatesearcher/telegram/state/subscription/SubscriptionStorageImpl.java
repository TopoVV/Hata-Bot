package com.topov.estatesearcher.telegram.state.subscription;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionStorageImpl implements SubscriptionStorage {
    private final Map<Long, List<Subscription>> subscriptions = new HashMap<>();

    @Override
    public void saveSubscription(long chatId, Subscription subscription) {
        if (!this.subscriptions.containsKey(chatId)) {
            this.subscriptions.put(chatId, new ArrayList<>());
        }

        this.subscriptions.get(chatId).add(subscription);
    }
}
