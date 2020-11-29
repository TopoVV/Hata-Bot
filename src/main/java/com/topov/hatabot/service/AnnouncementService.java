package com.topov.hatabot.service;

import com.topov.hatabot.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> filterNewAnnouncements(List<Announcement> announcements);
    void saveAnnouncements(List<Announcement> announcements);
}
