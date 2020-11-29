package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;
import java.util.Set;

public interface AnnouncementDao {
    void saveAnnouncements(List<Announcement> announcements);
    Set<Announcement> getAnnouncements();
    void removeExpired();
}
