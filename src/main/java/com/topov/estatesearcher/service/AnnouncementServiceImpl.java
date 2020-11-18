package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.AnnouncementDao;
import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementDao announcementDao;
    private final NotificationService notificationService;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementDao announcementDao, NotificationService notificationService) {
        this.announcementDao = announcementDao;
        this.notificationService = notificationService;
    }

    @Override
    public void saveAnnouncementsAndNotifySubscribers(List<Announcement> announcements) {
        final List<Announcement> storedAnnouncements = this.announcementDao.getAnnouncements();
        if (storedAnnouncements.isEmpty()) {
            this.announcementDao.saveAnnouncements(announcements);
        } else {
            log.info("{} new announcements detected", announcements.size());
            announcements.stream()
                .filter(announcement -> !storedAnnouncements.contains(announcement))
                .forEach(announcement -> {
                    this.announcementDao.saveAnnouncement(announcement);
                    this.notificationService.notifySubscribers(announcement);
                });
        }
    }
}
