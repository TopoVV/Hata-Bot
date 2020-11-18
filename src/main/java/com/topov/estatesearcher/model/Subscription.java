package com.topov.estatesearcher.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Long subscriptionsId;
    private String  chatId;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer cityId;
    private String cityName;

    public Subscription(String chatId) {
        this.chatId = chatId;
    }

    public Subscription(Subscription subscription) {
        this.subscriptionsId = subscription.subscriptionsId;
        this.chatId = subscription.chatId;
        this.minPrice = subscription.minPrice;
        this.maxPrice = subscription.maxPrice;
        this.cityId = subscription.cityId;
        this.cityName = subscription.cityName;
    }

    @Override
    public String toString() {
        final String template = "[id = %d | min price = %d | max price = %d | city = %s]";
        return String.format(template, subscriptionsId, minPrice, maxPrice, cityName);
    }
}
