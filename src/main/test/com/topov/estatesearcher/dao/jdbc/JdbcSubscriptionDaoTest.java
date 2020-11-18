package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.model.Subscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class JdbcSubscriptionDaoTest extends DaoTests {
    private final JdbcSubscriptionDao jdbcSubscriptionDao;

    @Autowired
    public JdbcSubscriptionDaoTest(DataSource dataSource) {
        this.jdbcSubscriptionDao = new JdbcSubscriptionDao(dataSource);
    }

    @Test
    void saveSubscription() {
        this.jdbcSubscriptionDao.saveSubscription(new Subscription("1"));
    }

    @Test
    void deleteSubscription() { this.jdbcSubscriptionDao.deleteSubscription(1L); }
}
