package com.topov.estatesearcher.service;

import com.topov.estatesearcher.model.Announcement;

import java.util.List;

public interface NotificationService {
    void notifySubscribers(List<Announcement> announcements);
}
