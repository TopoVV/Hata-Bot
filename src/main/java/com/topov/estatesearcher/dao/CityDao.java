package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.City;

import java.util.Optional;
import java.util.Set;

public interface CityDao {
    Set<City> getCities();
    Optional<City> getCity(int cityId);
}
