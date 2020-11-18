package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.CityDao;
import com.topov.estatesearcher.model.City;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Profile(value = "dev")
public class JdbcCityDao implements CityDao {
    private static final String SELECT_ALL_CITIES = "SELECT * FROM cities";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCityDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<City> getCities() {
        return jdbcTemplate.query(SELECT_ALL_CITIES, (rs, rowNum) -> {
            final City city = new City();
            city.setCityId(rs.getInt("city_id"));
            city.setCityName(rs.getString("city_name"));
            return city;
        });
    }

    @Override
    public Optional<City> getCity(int cityId) {
        return Optional.empty();
    }
}
