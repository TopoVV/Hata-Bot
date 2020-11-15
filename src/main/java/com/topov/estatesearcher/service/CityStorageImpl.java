package com.topov.estatesearcher.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.topov.estatesearcher.model.City;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CityStorageImpl implements CityStorage {
    private final Set<City> cities = new HashSet<>();

    @PostConstruct
    void init() {
        cities.add(new City(1,"Kiev"));
        cities.add(new City(2, "Odessa"));
    }

    @Override
    public Set<City> getCities() {
        return ImmutableSet.copyOf(cities);
    }

    @Override
    public Optional<City> getCity(int cityId) {
        return cities.stream()
            .filter(city -> city.getCityId().equals(cityId))
            .findFirst();
    }

}
