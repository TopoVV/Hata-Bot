package com.topov.estatesearcher.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Long subscriptionId;
    private String  chatId;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer cityId;
    private String cityName;

    public Subscription(String chatId) {
        this.chatId = chatId;
    }

    public Subscription(Subscription subscription) {
        this.subscriptionId = subscription.subscriptionId;
        this.chatId = subscription.chatId;
        this.minPrice = subscription.minPrice;
        this.maxPrice = subscription.maxPrice;
        this.cityId = subscription.cityId;
        this.cityName = subscription.cityName;
    }

    @Override
    public String toString() {
        final String template = "[id = %d | min price = %d | max price = %d | city = %s]";
        return String.format(template, subscriptionId, minPrice, maxPrice, cityName);
    }
}
