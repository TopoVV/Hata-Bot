package com.topov.hatabot.dao;

import com.topov.hatabot.model.Announcement;

import java.util.List;
import java.util.Set;

public interface AnnouncementDao {
    void saveAnnouncements(List<Announcement> announcements);
    Set<Announcement> getAnnouncements();
    void removeExpired();
}
