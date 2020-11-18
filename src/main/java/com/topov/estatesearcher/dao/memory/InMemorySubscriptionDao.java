package com.topov.estatesearcher.dao.memory;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Subscription;
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
    private final Map<Long, List<Subscription>> subscriptions = new HashMap<>();

    @PostConstruct
    void init() {
        log.info("In memory subscription DAO is initialized");
        this.subscriptions.put(517142416L, Collections.singletonList(new Subscription(String.valueOf(517142416L))));
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
