package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Subscription;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class InMemorySubscriptionDao implements SubscriptionDao {
    private final Map<Long, List<Subscription>> subscriptions = new HashMap<>();

    @PostConstruct
    void init() {
        this.subscriptions.put(517142416L, Collections.singletonList(new Subscription(517142416L)));
    }

    @Override
    public void saveSubscription(long chatId, Subscription subscription) {
        if (!this.subscriptions.containsKey(chatId)) {
            this.subscriptions.put(chatId, new ArrayList<>());
        }

        this.subscriptions.get(chatId).add(subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        final List<Subscription> subscriptions = this.subscriptions.values()
            .stream()
            .flatMap(Collection::stream)
            .collect(toList());

        return Collections.unmodifiableList(subscriptions);
    }
}
