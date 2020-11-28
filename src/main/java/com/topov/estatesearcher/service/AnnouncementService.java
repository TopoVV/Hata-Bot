package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> filterNewAnnouncements(List<Announcement> announcements);
    void saveAnnouncements(List<Announcement> announcements);
}
