package com.topov.estatesearcher.model;

import lombok.Getter;

@Getter
public class City {
    private final Integer cityId;
    private final String cityName;

    public City(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
