package com.topov.estatesearcher.dao;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;

public interface AnnouncementDao {
    void saveAnnouncements(List<Announcement> announcements);
    List<Announcement> getAnnouncements();
    void saveAnnouncement(Announcement announcement);
}
