package com.topov.hatabot.dao.jdbc;

import com.topov.hatabot.model.Announcement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;

public class JdbcAnnouncementDaoTest extends DaoTests {
    private final JdbcAnnouncementDao jdbcAnnouncementDao;

    @Autowired
    public JdbcAnnouncementDaoTest(DataSource dataSource) {
        this.jdbcAnnouncementDao = new JdbcAnnouncementDao(dataSource);
    }

    @Test
    void savesBatch() {
        jdbcAnnouncementDao.saveAnnouncements(Arrays.asList(
            new Announcement("11231", 1, LocalDateTime.now(), "desc1", "city1"),
            new Announcement("11232", 2, LocalDateTime.now(), "desc2", "city2"),
            new Announcement("11233", 3, LocalDateTime.now(), "desc3", "city3")
        ));
    }
}
