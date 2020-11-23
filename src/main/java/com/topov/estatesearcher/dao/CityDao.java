package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.City;

import java.util.List;

public interface CityDao {
    List<City> getCities();
    City getCity(String city);
}
