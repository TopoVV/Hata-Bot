package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.City;

import java.util.Optional;
import java.util.Set;

public interface CityService {
    Set<City> getCities();
    Optional<City> getCity(int cityId);
}