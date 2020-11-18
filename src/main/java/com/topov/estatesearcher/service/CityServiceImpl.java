package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.CityDao;
import com.topov.estatesearcher.model.City;
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
        return cityDao.getCities();
    }

    public Optional<City> getCity(int cityId) {
        return cityDao.getCity(cityId);
    }
}
