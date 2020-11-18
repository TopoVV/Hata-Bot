package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class JdbcSubscriptionDao implements SubscriptionDao {
    private static final String SELECT_ALL_SUBSCRIPTIONS = "SELECT * FROM subscriptions";
    private static final String SELECT_SUBSCRIPTION_BY_ID_AND_CHAT_ID =
        "SELECT * FROM subscriptions WHERE subscription_id = :subscriptionId AND chat_id = :chatId";
    private static final String INSERT_SUBSCRIPTION =
        "INSERT INTO subscriptions (chat_id, min_price, max_price, city_id, city_name) " +
            "VALUES (:chatId, :minPrice, :maxPrice, :cityId, :cityName)";
    private static final String DELETE_SUBSCRIPTION = "DELETE FROM subscriptions WHERE subscription_id = :subscriptionId";
//"INSERT INTO subscriptions (subscription_id, chat_id, min_price, max_price, city_id) " +
//    "VALUES (:subscriptionId, :chatId, :minPrice, :maxPrice, :cityId) " +
//    "ON CONFLICT (subscription_id) DO UPDATE SET min_price = :minPrice, max_price = :maxPrice";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcSubscriptionDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    void init() {
        log.info("JDBC subscription DAO is initialized");
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
       // params.addValue("subscriptionId", subscription.getSubscriptionsId());
        params.addValue("chatId", String.valueOf(subscription.getChatId()));
        params.addValue("minPrice", subscription.getMinPrice());
        params.addValue("maxPrice", subscription.getMaxPrice());
        params.addValue("cityId", subscription.getCityId());
        params.addValue("cityName", subscription.getCityName());

        this.jdbcTemplate.update(INSERT_SUBSCRIPTION, params);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
            return this.jdbcTemplate.query(SELECT_ALL_SUBSCRIPTIONS, (rs, rowNum) -> {
                final Subscription obj = new Subscription();
                obj.setSubscriptionId(rs.getLong("subscription_id"));
                obj.setChatId(rs.getString("chat_id"));
                obj.setCityId(rs.getInt("city_id"));
                obj.setMinPrice(rs.getInt("min_price"));
                obj.setMaxPrice(rs.getInt("max_price"));
                obj.setCityName(rs.getString("city_name"));
                return obj;
            });
    }

    @Override
    public Optional<Subscription> findSubscription(long subscriptionId, long chatId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("subscriptionId", subscriptionId);
        params.addValue("chatId", String.valueOf(chatId));

        try {
            return this.jdbcTemplate.queryForObject(SELECT_SUBSCRIPTION_BY_ID_AND_CHAT_ID, params, (rs, rowNum) -> {
                final Subscription subscription = new Subscription();
                subscription.setChatId(rs.getString("chat_id"));
                subscription.setSubscriptionId(rs.getLong("subscription_id"));
                return Optional.of(subscription);
            });
        } catch (EmptyResultDataAccessException e) {
            log.error("Empty result set");
            return Optional.empty();
        }
    }

    @Override
    public void deleteSubscription(Long subscriptionId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("subscriptionId", subscriptionId);

        this.jdbcTemplate.update(DELETE_SUBSCRIPTION, params);
    }
}
