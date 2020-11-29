package com.topov.hatabot.factory;

import com.topov.hatabot.model.Announcement;
import com.topov.hatabot.telegram.notification.Notification;

public interface NotificationFactory {
    Notification createNotification(String userId, Announcement announcement);
}
