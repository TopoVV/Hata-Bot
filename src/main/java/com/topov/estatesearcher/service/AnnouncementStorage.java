package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;
import java.util.Set;

public interface AnnouncementStorage {
    void saveAnnouncements(List<Announcement> announcements);
    Set<Announcement> getAnnouncements();
    void saveAnnouncement(Announcement announcement);
}
