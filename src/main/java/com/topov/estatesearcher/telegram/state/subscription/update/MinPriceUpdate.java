package com.topov.estatesearcher.telegram.state.subscription.update;

import com.topov.estatesearcher.model.Subscription;
import lombok.ToString;


@ToString
public class MinPriceUpdate implements SubscriptionUpdate {
    private final int newMinPrice;

    public MinPriceUpdate(int newMinPrice) {
        this.newMinPrice = newMinPrice;
    }

    @Override
    public Subscription update(Subscription old) {
        old.setMaxPrice(this.newMinPrice);
        return new Subscription(old);
    }
}
