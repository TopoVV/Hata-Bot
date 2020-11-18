package com.topov.estatesearcher.telegram.state.subscription.update;

import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.model.Subscription;

public class CityUpdate implements SubscriptionUpdate {
    private final int cityId;
    private final String cityName;

    public CityUpdate(City city) {
        this.cityId = city.getCityId();
        this.cityName = city.getCityName();
    }

    @Override
    public Subscription update(Subscription old) {
        old.setCityId(this.cityId);
        old.setCityName(this.cityName);
        return new Subscription(old);
    }
}
