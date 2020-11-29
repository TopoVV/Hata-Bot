package com.topov.hatabot.service;

import com.topov.hatabot.dao.SubscriptionDao;
import com.topov.hatabot.factory.NotificationFactory;
import com.topov.hatabot.model.Announcement;
import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.telegram.HataBot;
import com.topov.hatabot.telegram.notification.Notification;
import com.topov.hatabot.utils.AnnouncementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final HataBot hataBot;
    private final SubscriptionDao subscriptionDao;
    private final NotificationFactory notificationFactory;
    private final ReentrantLock lock = new ReentrantLock(true);


    @Autowired
    public NotificationServiceImpl(HataBot hataBot, SubscriptionDao subscriptionDao, NotificationFactory notificationFactory) {
        this.hataBot = hataBot;
        this.subscriptionDao = subscriptionDao;
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void notifySubscribers(List<Announcement> announcements) {
        this.lock.lock();
        Collection<Subscription> subscriptions = this.subscriptionDao.getAllSubscriptions();
        subscriptions.forEach(subscription -> this.notifySubscriber(subscription, announcements));
        this.lock.unlock();
    }

    private void notifySubscriber(Subscription subscription, List<Announcement> announcements) {
        announcements.forEach(announcement -> {
            if (AnnouncementUtils.announcementMatchesSubscription(announcement, subscription)) {
                createAndSendNotification(subscription, announcement);
            }
        });
    }

    private void createAndSendNotification(Subscription subscription, Announcement announcement) {
        final String userId = subscription.getUserId();
        final Notification notification = this.notificationFactory.createNotification(userId, announcement);
        this.hataBot.sendNotification(notification);
    }
}
