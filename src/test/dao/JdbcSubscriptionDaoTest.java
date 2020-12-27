package dao;

import com.topov.hatabot.model.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.topov.hatabot.dao.jdbc.SubscriptionJdbcDao;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

public class JdbcSubscriptionDaoTest extends DaoTests {
    private final SubscriptionJdbcDao jdbcSubscriptionDao;

    @Autowired
    public JdbcSubscriptionDaoTest(DataSource dataSource) {
        this.jdbcSubscriptionDao = new SubscriptionJdbcDao(dataSource);
    }

    @Test
    void saveSubscription() {
        Assertions.assertDoesNotThrow(() -> this.jdbcSubscriptionDao.saveSubscription(new Subscription("1")));
    }

    @Test
    void deleteSubscription() { Assertions.assertDoesNotThrow(() -> this.jdbcSubscriptionDao.deleteSubscription(1L)); }
}
