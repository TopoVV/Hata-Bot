package com.topov.hatabot.dao.jdbc;

import com.topov.hatabot.dao.UserJdbcDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Log4j2
@Repository
public class UserJdbcDaoImpl implements UserJdbcDao {
    private static final String SELECT_USER_BY_ID =
        "SELECT * FROM bot_users WHERE user_id = :userId";

    private static final String INSERT_USER =
        "INSERT INTO bot_users (user_id, is_activated) VALUES(:userId, :isActivated)";

    private static final String ACTIVATE_USER =
        "UPDATE users SET is_activated = true WHERE user_id = :userId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserJdbcDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        try {
            final User user = this.jdbcTemplate.queryForObject(SELECT_USER_BY_ID, params, (rs, rowNum) -> {
                final User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setActivated(rs.getBoolean("is_activated"));
                return u;
            });

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", user.getUserId());
            params.addValue("isActivated", user.isActivated());
            this.jdbcTemplate.update(INSERT_USER,   params);
        } catch (DataAccessException e) {
            log.error("Error when saving user", e);
        }
    }

    @Override
    public void activateUser(String userId) {
        try {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);
            this.jdbcTemplate.update(ACTIVATE_USER, params);
        } catch (DataAccessException e) {
            log.error("Error when activating user", e);
        }
    }
}
