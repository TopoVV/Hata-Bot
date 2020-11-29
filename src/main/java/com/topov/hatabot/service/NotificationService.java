package com.topov.hatabot.service;

import com.topov.hatabot.model.Announcement;

import java.util.List;

public interface NotificationService {
    void notifySubscribers(List<Announcement> announcements);
}
