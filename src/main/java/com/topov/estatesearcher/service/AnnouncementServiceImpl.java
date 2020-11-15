package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Announcement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Log4j2
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementStorage announcementStorage;
    private final NotificationService notificationService;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementStorage announcementStorage, NotificationService notificationService) {
        this.announcementStorage = announcementStorage;
        this.notificationService = notificationService;
    }

    @Override
    public void saveAnnouncementsAndNotifySubscribers(List<Announcement> announcements) {
        final Set<Announcement> storedAnnouncements = this.announcementStorage.getAnnouncements();
        if (storedAnnouncements.isEmpty()) {
            this.announcementStorage.saveAnnouncements(announcements);
        } else {
            log.info("{} new announcements detected", announcements.size());
            announcements.stream()
                .filter(announcement -> !storedAnnouncements.contains(announcement))
                .forEach(announcement -> {
                    this.announcementStorage.saveAnnouncement(announcement);
                    this.notificationService.notifySubscribers(announcement);
                });
        }
    }
}
