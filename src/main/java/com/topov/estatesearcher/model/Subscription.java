package com.topov.estatesearcher.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Subscription {
    private Long chatId;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer cityId;

    public Subscription(Long chatId) {
        this.chatId = chatId;
    }

    public Subscription(Subscription subscription) {
        this.chatId = subscription.chatId;
        this.minPrice = subscription.getMinPrice();
        this.maxPrice = subscription.getMaxPrice();
        this.cityId = subscription.cityId;
    }
}
