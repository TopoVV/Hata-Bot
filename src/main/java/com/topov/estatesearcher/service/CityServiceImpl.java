package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.City;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class CityServiceImpl implements CityService {
    private final CityStorage cityStorage;

    @Autowired
    public CityServiceImpl(CityStorage cityStorage) {
        this.cityStorage = cityStorage;
    }

    @Override
    public Set<City> getCities() {
        return cityStorage.getCities();
    }

    public Optional<City> getCity(int cityId) {
        return cityStorage.getCity(cityId);
    }
}
