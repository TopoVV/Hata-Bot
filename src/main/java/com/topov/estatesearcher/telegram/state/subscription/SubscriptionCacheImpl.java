package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionUpdator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class SubscriptionCacheImpl implements SubscriptionCache {
    private final Map<Long, Subscription> subscriptions = new HashMap<>();

    @Override
    public void removeCachedSubscription(long chatId) {
        this.subscriptions.remove(chatId);
    }

    @Override
    public void modifySubscription(long chatId, SubscriptionUpdator subscriptionUpdator) {
        log.debug("Updating subscription for user {}. Modified: {}", chatId, subscriptionUpdator);
        final Subscription currentSubscription = this.subscriptions.getOrDefault(chatId, new Subscription());
        final Subscription merged = subscriptionUpdator.update(currentSubscription);
        this.subscriptions.put(chatId, merged);
    }

    @Override
    public Optional<Subscription> getCachedSubscription(Long chatId) {
        return Optional.ofNullable(subscriptions.get(chatId));
    }
}
