package com.topov.estatesearcher.dao.memory;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@Profile("dev-light")
public class InMemoryAnnouncementDao implements AnnouncementDao
{
    private final List<Announcement> storedAnnouncements = new ArrayList<>();

    @PostConstruct
    void init() {
        log.info("In memory announcement DAO is initialized");
    }

    @Override
    public void saveAnnouncements(List<Announcement> announcements) {
        log.info("Storing {} announcements", announcements.size());
        this.storedAnnouncements.addAll(announcements);
    }

    @Override
    public Set<Announcement> getAnnouncements() {
        return new HashSet<>(this.storedAnnouncements);
    }

    @Override
    public void removeExpired() {
        this.storedAnnouncements.removeIf(
            announcement -> Duration.between(announcement.getExtractionDateTime(), LocalDateTime.now()).toDays() > 3
        );
    }

}
