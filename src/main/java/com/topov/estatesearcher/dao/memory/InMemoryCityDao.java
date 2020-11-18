package com.topov.estatesearcher.dao.memory;

import com.topov.estatesearcher.dao.CityDao;
import com.topov.estatesearcher.model.City;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Profile("dev-light")
public class InMemoryCityDao implements CityDao {
    private final Set<City> cities = new HashSet<>();

    @PostConstruct
    void init() {
        log.info("In memory city DAO is initialized");
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
