package com.topov.hatabot.service;

import com.topov.hatabot.dao.AnnouncementDao;
import com.topov.hatabot.model.Announcement;
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
    public List<Announcement> filterNewAnnouncements(List<Announcement> extractedAnnouncements) {
        final Set<Announcement> storedAnnouncements = this.announcementDao.getAnnouncements();
        final List<Announcement> newAnnouncements = extractedAnnouncements.stream()
            .filter(announcement -> !storedAnnouncements.contains(announcement))
            .collect(Collectors.toCollection(ArrayList::new));

        log.info("{} new announcements detected", newAnnouncements.size());

        return newAnnouncements;

    }
    @Override
    public void saveAnnouncements(List<Announcement> announcements) {
        this.announcementDao.saveAnnouncements(announcements);
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void removeExpiredAnnouncements() {
        this.announcementDao.removeExpired();
    }

}
