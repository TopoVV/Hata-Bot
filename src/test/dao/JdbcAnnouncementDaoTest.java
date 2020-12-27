package dao;

import com.topov.hatabot.dao.jdbc.AnnouncementJdbcDao;
import com.topov.hatabot.model.Announcement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;

public class JdbcAnnouncementDaoTest extends DaoTests {
    private final AnnouncementJdbcDao jdbcAnnouncementDao;

    @Autowired
    public JdbcAnnouncementDaoTest(DataSource dataSource) {
        this.jdbcAnnouncementDao = new AnnouncementJdbcDao(dataSource);
    }

    @Test
    void savesBatch() {
        Assertions.assertDoesNotThrow(() ->
            jdbcAnnouncementDao.saveAnnouncements(Arrays.asList(
                new Announcement("11231", 1, LocalDateTime.now(), "desc1", "city1"),
                new Announcement("11232", 2, LocalDateTime.now(), "desc2", "city2"),
                new Announcement("11233", 3, LocalDateTime.now(), "desc3", "city3")
            ))
        );
    }
}
