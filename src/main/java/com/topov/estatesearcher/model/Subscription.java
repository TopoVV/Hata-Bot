package com.topov.estatesearcher.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Integer minPrice;
    private Integer maxPrice;
    private Integer cityId;

    public Subscription(Subscription subscription) {
        this.minPrice = subscription.getMinPrice();
        this.maxPrice = subscription.getMaxPrice();
        this.cityId = subscription.cityId;
    }
}
