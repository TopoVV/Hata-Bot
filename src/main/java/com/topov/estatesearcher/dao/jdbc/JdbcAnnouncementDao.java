package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Profile(value = "dev")
@Repository
public class JdbcAnnouncementDao implements AnnouncementDao {
    private static final String INSERT_ANNOUNCEMENT_SQL =
        "INSERT INTO announcements (url, price, extraction_date_time, description, city_name) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_ANNOUNCEMENTS =
        "SELECT * FROM announcements";

    private static final String REMOVE_EXPIRED_ANNOUNCEMENTS =
        "DELETE FROM announcements WHERE (EXTRACT(day FROM now()) - EXTRACT(day FROM extraction_date_time)) >= 3";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAnnouncementDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    void init() {
        log.info("JDBC announcement DAO is initialized");
    }

    @Override
    public void saveAnnouncements(List<Announcement> announcements) {
        try {
            int batchSize = 10;

            for (int j = 0; j < announcements.size(); j += batchSize) {
                final List<Announcement> batch = announcements.subList(j, Math.min(j + batchSize, announcements.size()));

                this.jdbcTemplate.batchUpdate(INSERT_ANNOUNCEMENT_SQL, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, batch.get(i).getUrl());
                        ps.setInt(2, batch.get(i).getPrice());
                        ps.setTimestamp(3, Timestamp.valueOf(batch.get(i).getExtractionDateTime()));
                        ps.setString(4, batch.get(i).getDescription());
                        ps.setString(5, batch.get(i).getCityName());
                    }

                    @Override
                    public int getBatchSize() {
                        return batch.size();
                    }
                });
            }
        } catch (DataAccessException e) {
            log.error("Announcement batch saving failed");
        }
    }

    @Override
    public Set<Announcement> getAnnouncements() {
        try {
            return new HashSet<>(this.jdbcTemplate.query(SELECT_ALL_ANNOUNCEMENTS, (Object[]) null, (rs, rowNum) -> {
                final Announcement announcement = new Announcement();
                announcement.setAnnouncementId(rs.getLong("announcement_id"));
                announcement.setUrl(rs.getString("url"));
                announcement.setPrice(rs.getInt("price"));
                announcement.setDescription(rs.getString("description"));
                final Timestamp extractionTimestamp = new Timestamp(rs.getDate("extraction_date_time").getTime());
                announcement.setExtractionDateTime(extractionTimestamp.toLocalDateTime());
                announcement.setCityName(rs.getString("city_name"));
                return announcement;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptySet();
        }
    }

    @Async
    @Override
    public void removeExpired() {
        try {
            log.info("Deleting old announcements");
            this.jdbcTemplate.execute(REMOVE_EXPIRED_ANNOUNCEMENTS);
        } catch (DataAccessException e) {
            log.error("Error during expired announcements removal", e);
        }
    }
}
