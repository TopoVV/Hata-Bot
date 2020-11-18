package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> getCities();
    Optional<City> getCity(int cityId);
}
