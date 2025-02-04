package com.topov.hatabot.dao.memory;

import com.topov.hatabot.dao.SubscriptionDao;
import com.topov.hatabot.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@Profile("dev-light")
public class InMemorySubscriptionDao implements SubscriptionDao {
    private final Map<String, List<Subscription>> subscriptions = new HashMap<>();

    @PostConstruct
    void init() {
        log.info("In memory subscription DAO is initialized");
        final String chatIdDefault = String.valueOf(517142416L);
        this.subscriptions.put(chatIdDefault, Collections.singletonList(new Subscription(chatIdDefault)));
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        final String chatId = subscription.getUserId();
        if (!this.subscriptions.containsKey(chatId)) {
            this.subscriptions.put(chatId, new ArrayList<>());
        }

        this.subscriptions.get(chatId).add(subscription);
    }

    @Override
    public List<Subscription> getAllUserSubscriptions(String userId) {
        final List<Subscription> subscriptions = this.subscriptions.get(userId);
        return Collections.unmodifiableList(subscriptions);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        final List<Subscription> subscriptions = this.subscriptions.values()
            .stream()
            .flatMap(Collection::stream)
            .collect(toList());

        return Collections.unmodifiableList(subscriptions);
    }

    @Override
    public Optional<Subscription> findSubscription(long subscriptionId, String userId) {
        return this.subscriptions.get(userId)
            .stream()
            .filter(s -> s.getSubscriptionId().equals(subscriptionId))
            .findFirst();
    }

    @Override
    public void deleteSubscription(Long subscriptionId) {
        this.subscriptions.values().stream()
            .filter(list -> list.stream()
                .anyMatch(subscription -> subscription.getSubscriptionId().equals(subscriptionId))
            ).findFirst()
            .ifPresent(list -> list.removeIf(subscription -> subscription.getSubscriptionId().equals(subscriptionId)));
    }
}
