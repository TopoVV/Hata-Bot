package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Log4j2
@Profile(value = "dev")
@Service
public class JdbcAnnouncementDao implements AnnouncementDao {
    private static final String INSERT_ANNOUNCEMENT_SQL =
        "INSERT INTO announcements (url, price, extraction_date_time, description, city_name) VALUES (?, ?, ?, ?, ?)";

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
        int batchSize = 100;

        for (int j = 0; j < announcements.size(); j+= batchSize) {
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
    }

    @Override
    public List<Announcement> getAnnouncements() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void saveAnnouncement(Announcement announcement) {
        this.jdbcTemplate.execute(INSERT_ANNOUNCEMENT_SQL, (PreparedStatementCallback<Boolean>) ps -> {
            ps.setString(1, announcement.getUrl());
            ps.setInt(2, announcement.getPrice());
            ps.setTimestamp(3, Timestamp.valueOf(announcement.getExtractionDateTime()));
            ps.setString(4, announcement.getDescription());
            ps.setString(5, announcement.getCityName());

            return ps.execute();
        });
    }
}
