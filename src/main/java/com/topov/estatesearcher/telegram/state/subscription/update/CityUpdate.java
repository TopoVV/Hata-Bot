package com.topov.estatesearcher.telegram.state.subscription.update;

import com.topov.estatesearcher.model.Subscription;

public class CityUpdate implements SubscriptionUpdate {
    private final int cityId;

    public CityUpdate(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public Subscription update(Subscription old) {
        old.setCityId(this.cityId);
        return new Subscription(old);
    }
}
