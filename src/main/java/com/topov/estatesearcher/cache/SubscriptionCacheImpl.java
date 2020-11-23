package com.topov.estatesearcher.cache;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.state.subscription.update.SubscriptionUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Since multiple users can create subscriptions {@link Subscription} simultaneously, the application stores managed
 * subscription object for each user if any. This class will maybe later be replaced with DBMS or Redis-like cache.
 */

@Log4j2
@Service
public class SubscriptionCacheImpl implements SubscriptionCache {
    private final Map<Long, Subscription> subscriptions = new HashMap<>();
    private final SubscriptionService subscriptionService;

    public SubscriptionCacheImpl(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void removeCachedSubscription(long chatId) {
        log.debug("Removing cached subscription for user: {}", chatId);
        this.subscriptions.remove(chatId);
    }

    /**
     * Performs modification of the subscription for user.
     * @param chatId - telegram chat id.
     * @param subscriptionUpdate - update operation (command pattern - like object), updates the instance of the
     * subscription and returns a copy with new values
     */
    @Override
    public void modifySubscription(long chatId, SubscriptionUpdate subscriptionUpdate) {
        log.debug("Updating subscription for user {}. Modified: {}", chatId, subscriptionUpdate);
        final Subscription currentSubscription = this.subscriptions.getOrDefault(chatId, new Subscription(String.valueOf(chatId)));
        final Subscription merged = subscriptionUpdate.update(currentSubscription);
        this.subscriptions.put(chatId, merged);
    }

    @Override
    public Optional<Subscription> getCachedSubscription(Long chatId) {
        log.debug("Retrieving cached subscription for user: {}", chatId);
        return Optional.ofNullable(subscriptions.get(chatId));
    }

    @Override
    public boolean flush(Long chatId) {
        if (this.subscriptions.containsKey(chatId)) {
            final Subscription subscription = this.subscriptions.remove(chatId);
            this.subscriptionService.saveSubscription(subscription);
            return true;
        }
        return false;
    }
}
