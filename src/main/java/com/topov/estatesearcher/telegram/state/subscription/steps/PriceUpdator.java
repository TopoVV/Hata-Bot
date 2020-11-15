package com.topov.estatesearcher.telegram.state.subscription.steps;

import com.topov.estatesearcher.telegram.state.subscription.Subscription;
import lombok.ToString;


@ToString
public class PriceUpdator implements SubscriptionUpdator {
    private final int price;

    public PriceUpdator(int price) {
        this.price = price;
    }

    @Override
    public Subscription update(Subscription old) {
        old.setPrice(this.price);
        return new Subscription(old);
    }
}
