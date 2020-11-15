package com.topov.estatesearcher.telegram.state.subscription.update;

import com.topov.estatesearcher.model.Subscription;

public class MaxPriceUpdate implements SubscriptionUpdate {
    private final int newMaxPrice;

    public MaxPriceUpdate(int newMaxPrice) {
        this.newMaxPrice = newMaxPrice;
    }

    @Override
    public Subscription update(Subscription old) {
        old.setMaxPrice(this.newMaxPrice);
        return new Subscription(old);
    }
}
