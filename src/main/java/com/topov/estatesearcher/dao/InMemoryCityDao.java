package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.City;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InMemoryCityDao implements CityDao {
    private final Set<City> cities = new HashSet<>();

    @PostConstruct
    void init() {
        cities.add(new City(1,"Kiev"));
        cities.add(new City(2, "Odessa"));
    }

    @Override
    public Set<City> getCities() {
        return Collections.unmodifiableSet(cities);
    }

    @Override
    public Optional<City> getCity(int cityId) {
        return cities.stream()
            .filter(city -> city.getCityId().equals(cityId))
            .findFirst();
    }

}
