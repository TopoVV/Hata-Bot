package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.City;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CityDao {
    Collection<City> getCities();
    Optional<City> getCity(int cityId);
}
