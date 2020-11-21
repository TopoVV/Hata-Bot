package com.topov.estatesearcher.dao.memory;

import com.topov.estatesearcher.dao.CityDao;
import com.topov.estatesearcher.model.City;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Profile("dev-light")
public class InMemoryCityDao implements CityDao {
    private final List<City> cities = new ArrayList<>();

    @PostConstruct
    void init() {
        log.info("In memory city DAO is initialized");
        this.cities.add(new City(1,"Kiev"));
        this.cities.add(new City(2, "Odessa"));
    }

    @Override
    public List<City> getCities() {
        return this.cities;
    }

    @Override
    public Optional<City> getCity(int cityId) {
        return this.cities.stream()
            .filter(city -> city.getCityId().equals(cityId))
            .findFirst();
    }

}
