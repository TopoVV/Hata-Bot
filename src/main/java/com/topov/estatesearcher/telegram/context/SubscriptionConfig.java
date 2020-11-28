package com.topov.estatesearcher.telegram.context;

import com.topov.estatesearcher.model.City;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Optional;

@Setter
public class SubscriptionConfig {
    @Getter
    private final String chatId;
    private Integer minPrice;
    private Integer maxPrice;
    private City city;

    public SubscriptionConfig(String chatId) {
        this.chatId = chatId;
    }

    public SubscriptionConfig(SubscriptionConfig subscriptionConfig) {
        this.chatId = subscriptionConfig.chatId;
        this.maxPrice = subscriptionConfig.maxPrice;
        this.minPrice = subscriptionConfig.minPrice;
        this.city = subscriptionConfig.city;
    }

    public boolean isConfigured() {
        return this.minPrice != null ||
            this.maxPrice != null ||
            this.city != null;
    }


    public  Optional<City> getCity() {
        return Optional.ofNullable(this.city);
    }
    public  Optional<Integer> getMinPrice() { return Optional.ofNullable(this.minPrice); }
    public  Optional<Integer> getMaxPrice() { return Optional.ofNullable(this.maxPrice); }
}
