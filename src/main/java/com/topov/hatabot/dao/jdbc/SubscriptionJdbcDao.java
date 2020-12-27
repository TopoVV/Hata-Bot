package com.topov.hatabot.dao.jdbc;

import com.topov.hatabot.dao.SubscriptionDao;
import com.topov.hatabot.model.City;
import com.topov.hatabot.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@Profile(value = "prod")
public class SubscriptionJdbcDao implements SubscriptionDao {
    private static final String SELECT_ALL_SUBSCRIPTIONS =
        "SELECT * FROM subscriptions";

    private static final String SELECT_ALL_USER_SUBSCRIPTIONS =
        "SELECT * FROM subscriptions WHERE user_id = :userId";

    private static final String SELECT_SUBSCRIPTION_BY_ID_AND_CHAT_ID =
        "SELECT * FROM subscriptions WHERE subscription_id = :subscriptionId AND user_id = :userId";

    private static final String INSERT_SUBSCRIPTION =
        "INSERT INTO subscriptions (user_id, min_price, max_price, city_id, city_name) " +
            "VALUES (:userId, :minPrice, :maxPrice, :cityId, :cityName)";

    private static final String DELETE_SUBSCRIPTION =
        "DELETE FROM subscriptions WHERE subscription_id = :subscriptionId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriptionJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    void init() {
        log.info("JDBC subscription DAO is initialized");
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", subscription.getUserId());
        params.addValue("minPrice", subscription.getMinPrice());
        params.addValue("maxPrice", subscription.getMaxPrice());
        params.addValue("cityId", subscription.getCity().map(City::getCityId).orElse(null));
        params.addValue("cityName", subscription.getCity().map(City::getCityName).orElse(null));

        this.jdbcTemplate.update(INSERT_SUBSCRIPTION, params);
    }

    @Override
    public List<Subscription> getAllUserSubscriptions(String userId) {
        try {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);

            return this.jdbcTemplate.query(SELECT_ALL_USER_SUBSCRIPTIONS, params, subscriptionRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        try {
            return this.jdbcTemplate.query(SELECT_ALL_SUBSCRIPTIONS, subscriptionRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Subscription> findSubscription(long subscriptionId, String userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("subscriptionId", subscriptionId);
        params.addValue("userId", userId);

        try {
            return this.jdbcTemplate.queryForObject(SELECT_SUBSCRIPTION_BY_ID_AND_CHAT_ID, params, (rs, rowNum) -> {
                final Subscription subscription = new Subscription();
                subscription.setUserId(rs.getString("user_id"));
                subscription.setSubscriptionId(rs.getLong("subscription_id"));
                return Optional.of(subscription);
            });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteSubscription(Long subscriptionId) {
        try {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("subscriptionId", subscriptionId);
            this.jdbcTemplate.update(DELETE_SUBSCRIPTION, params);
        } catch (DataAccessException e) {
            log.error("Subscription delete failed");
        }
    }

    private static final RowMapper<Subscription> subscriptionRowMapper =  (rs, rowNum) -> {
        final Subscription obj = new Subscription();
        final int cityId = rs.getInt("city_id");
        final String cityName = rs.getString("city_name");
        if (cityId != 0 && cityName != null) {
            final City city = new City(cityId, cityName);
            obj.setCity(city);
        } else {
            obj.setCity(null);
        }

        obj.setSubscriptionId(rs.getLong("subscription_id"));
        obj.setUserId(rs.getString("user_id"));
        obj.setMinPrice(rs.getInt("min_price"));
        obj.setMaxPrice(rs.getInt("max_price"));
        return obj;
    };
}
