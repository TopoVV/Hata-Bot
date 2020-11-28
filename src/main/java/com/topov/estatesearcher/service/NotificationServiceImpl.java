package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.factory.NotificationFactory;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.EstateBot;
import com.topov.estatesearcher.telegram.notification.Notification;
import com.topov.estatesearcher.utils.AnnouncementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final EstateBot estateBot;
    private final SubscriptionDao subscriptionDao;
    private final NotificationFactory notificationFactory;
    private final ReentrantLock lock = new ReentrantLock(true);


    @Autowired
    public NotificationServiceImpl(EstateBot estateBot, SubscriptionDao subscriptionDao, NotificationFactory notificationFactory) {
        this.estateBot = estateBot;
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
                final Notification notification = notificationFactory.createNotification(subscription.getUserId(), announcement);
                this.estateBot.sendNotification(notification);
            }
        });
    }
}
