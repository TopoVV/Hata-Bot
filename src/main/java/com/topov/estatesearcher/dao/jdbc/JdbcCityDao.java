package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.CityDao;
import com.topov.estatesearcher.model.City;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Profile(value = "dev")
public class JdbcCityDao implements CityDao {
    private static final String SELECT_ALL_CITIES = "SELECT DISTINCT * FROM cities";
    private static final String SELECT_CITY_BY_ID = "SELECT * FROM cities WHERE city_id = :cityId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCityDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    void init() {
        log.info("JDBC city DAO is initialized");
    }

    @Override
    public List<City> getCities() {
        return jdbcTemplate.query(SELECT_ALL_CITIES, cityRowMapper);
    }

    @Override
    public Optional<City> getCity(int cityId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cityId", cityId);

        final City city = this.jdbcTemplate.queryForObject(SELECT_CITY_BY_ID, params, cityRowMapper);

        return Optional.ofNullable(city);
    }

    private static final RowMapper<City> cityRowMapper = (rs, rowNum) -> {
        final City obj = new City();
        obj.setCityId(rs.getInt("city_id"));
        obj.setCityName(rs.getString("city_name"));
        return obj;
    };
}
