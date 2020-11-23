package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.City;

import java.util.List;

public interface CityService {
    List<City> getCities();
    City getCity(String city);
}
