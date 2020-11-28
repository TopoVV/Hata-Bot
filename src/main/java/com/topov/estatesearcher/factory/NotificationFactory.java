package com.topov.estatesearcher.factory;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.telegram.notification.Notification;

public interface NotificationFactory {
    Notification createNotification(String userId, Announcement announcement);
}
