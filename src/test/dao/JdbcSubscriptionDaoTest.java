package dao;

import com.topov.hatabot.model.Subscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.topov.hatabot.dao.jdbc.SubscriptionJdbcDao;

import javax.sql.DataSource;

public class JdbcSubscriptionDaoTest extends DaoTests {
    private final SubscriptionJdbcDao jdbcSubscriptionDao;

    @Autowired
    public JdbcSubscriptionDaoTest(DataSource dataSource) {
        this.jdbcSubscriptionDao = new SubscriptionJdbcDao(dataSource);
    }

    @Test
    void saveSubscription() {
        this.jdbcSubscriptionDao.saveSubscription(new Subscription("1"));
    }

    @Test
    void deleteSubscription() { this.jdbcSubscriptionDao.deleteSubscription(1L); }
}
