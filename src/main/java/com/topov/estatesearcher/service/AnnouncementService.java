package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    void saveAnnouncementsAndNotifySubscribers(List<Announcement> announcements);
}
