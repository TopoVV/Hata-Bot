package com.topov.estatesearcher.dao.jdbc;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.Name;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Log4j2
@Profile(value = "dev")
@Service
public class JdbcAnnouncementDao implements AnnouncementDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_ANNOUNCEMENT_SQL =
        "INSERT INTO announcements (url, price, extraction_date_time, description, city_name) VALUES (:url, :price, :extractionDateTime, :description, :cityName)";

    public JdbcAnnouncementDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

            final List<Map<String, Object>> batchValues = batch.stream()
                .map(this::createMapParameterSource)
                .map(MapSqlParameterSource::getValues)
                .collect(toList());

            this.jdbcTemplate.batchUpdate(INSERT_ANNOUNCEMENT_SQL, batchValues.toArray(new Map[announcements.size()]));
        }
    }

    @Override
    public Set<Announcement> getAnnouncements() {
        return null;
    }

    @Override
    public void saveAnnouncement(Announcement announcement) {
        final MapSqlParameterSource params = createMapParameterSource(announcement);

        this.jdbcTemplate.update(INSERT_ANNOUNCEMENT_SQL, params);
    }

    private MapSqlParameterSource createMapParameterSource(Announcement announcement) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("url", announcement.getUrl());
        params.addValue("price", announcement.getPrice());
        params.addValue("extractionDateTime", announcement.getExtractionDateTime());
        params.addValue("description", announcement.getDescription());
        params.addValue("cityName", announcement.getCityName());
        return params;
    }

}
