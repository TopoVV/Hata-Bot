package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementDao announcementDao;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementDao announcementDao) {
        this.announcementDao = announcementDao;
    }

    @Override
    public List<Announcement> filterNewAnnouncements(List<Announcement> announcements) {
        final Set<Announcement> storedAnnouncements = this.announcementDao.getAnnouncements();
        log.info("{} new announcements detected", announcements.size());
        return announcements.stream()
            .filter(announcement -> !storedAnnouncements.contains(announcement))
            .collect(Collectors.toCollection(ArrayList::new));

    }
    @Override
    public void saveAnnouncements(List<Announcement> announcements) {
        this.announcementDao.saveAnnouncements(announcements);
    }

    @Scheduled(fixedDelay = 1000*60*60*24*3)
    public void removeExpiredAnnouncements() {
        this.announcementDao.removeExpired();
    }

}
