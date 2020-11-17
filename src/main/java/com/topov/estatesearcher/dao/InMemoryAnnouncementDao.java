package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class InMemoryAnnouncementDao implements AnnouncementDao {
    private final Set<Announcement> storedAnnouncements = new HashSet<>();

    @Override
    public void saveAnnouncements(List<Announcement> announcements) {
        log.info("Storing {} announcements", announcements.size());
        this.storedAnnouncements.addAll(announcements);
    }

    @Override
    public Set<Announcement> getAnnouncements() {
        return Collections.unmodifiableSet(this.storedAnnouncements);
    }

    @Override
    public void saveAnnouncement(Announcement announcement) {
        log.info("Storing announcement: \n{}", announcement);
        this.storedAnnouncements.add(announcement);
    }
}
