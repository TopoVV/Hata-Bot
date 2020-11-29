package com.topov.hatabot.model;

import com.topov.hatabot.telegram.context.SubscriptionConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Long subscriptionId;
    private String userId;
    private Integer minPrice;
    private Integer maxPrice;
    private City city;

    public Subscription(String userId) {
        this.userId = userId;
    }

    public Subscription(Subscription subscription) {
        this.subscriptionId = subscription.subscriptionId;
        this.userId = subscription.userId;
        this.minPrice = subscription.minPrice;
        this.maxPrice = subscription.maxPrice;
        this.city = subscription.city;
    }

    public Subscription(SubscriptionConfig config) {
        this.userId = config.getChatId();
        this.minPrice = config.getMinPrice().orElse(0);
        this.maxPrice = config.getMaxPrice().orElse(Integer.MAX_VALUE);
        this.city = config.getCity().orElse(null);
    }

    public Optional<City> getCity() {
        return Optional.ofNullable(city);
    }

}
