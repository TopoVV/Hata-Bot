package com.topov.hatabot.dao;

import com.topov.hatabot.model.City;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    List<City> getCities();
    Optional<City> getCity(String cityName);
    Optional<City> getCity(Integer cityId);
}
