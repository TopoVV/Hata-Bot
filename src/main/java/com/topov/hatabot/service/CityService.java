package com.topov.hatabot.service;

import com.topov.hatabot.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> getCities();
    Optional<City> getCity(String city);
    Optional<City> getCity(Integer cityId);
}
