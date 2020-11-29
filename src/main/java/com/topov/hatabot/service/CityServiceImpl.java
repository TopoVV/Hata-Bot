package com.topov.hatabot.service;

import com.topov.hatabot.dao.CityDao;
import com.topov.hatabot.model.City;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CityServiceImpl implements CityService {
    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<City> getCities() {
        return this.cityDao.getCities();
    }

    @Override
    public Optional<City> getCity(String city) {
        return this.cityDao.getCity(city);
    }

    @Override
    public Optional<City> getCity(Integer cityId) {
        return this.cityDao.getCity(cityId);
    }
}
