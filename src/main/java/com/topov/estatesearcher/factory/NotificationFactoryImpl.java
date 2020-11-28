package com.topov.estatesearcher.factory;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.notification.Notification;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.utils.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class NotificationFactoryImpl implements NotificationFactory {
    private final UserContextService contextService;

    @Autowired
    public NotificationFactoryImpl(UserContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public Notification createNotification(String userId, Announcement announcement) {
        final UserContext context = this.contextService.getContextForUser(userId);
        final String template = MessageHelper.getMessage("notification.template", context);
        final String city = announcement.getCityName();
        final String link = announcement.getUrl();
        final Integer price = announcement.getPrice();
        final String description = announcement.getDescription();
        final String notificationText = MessageFormat.format(template, city, link, price, description);
        return new Notification(userId, notificationText);
    }
}
