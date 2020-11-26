package com.topov.estatesearcher.model;

import com.topov.estatesearcher.telegram.context.SubscriptionConfig;
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

    public Subscription(SubscriptionConfig config) {
        this.chatId = config.getChatId();
        this.minPrice = config.getMinPrice();
        this.maxPrice = config.getMaxPrice();
        this.cityName = config.getCity().map(City::getCityName).orElse(null);
        this.cityId = config.getCity().map(City::getCityId).orElse(null);
    }

    @Override
    public String toString() {
        final String minPrice = this.minPrice == null ? "Not specified" : this.minPrice.toString();
        final String maxPrice = this.maxPrice == null ? "Not specified" : this.maxPrice.toString();
        final String cityName = this.cityName == null ? "Not specified" : this.cityName;
        final String id = this.subscriptionId == null ? "" : String.format("id = %d", this.subscriptionId);
        final String template = "%s\nmin price = %s\nmax price = %s\ncity = %s";
        return String.format(template, id, minPrice, maxPrice, cityName);
    }
}
