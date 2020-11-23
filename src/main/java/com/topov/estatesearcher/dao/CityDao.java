package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.City;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    List<City> getCities();
    Optional<City> getCity(String cityName);
    Optional<City> getCity(Integer cityId);
}
