package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.EstateBot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final EstateBot estateBot;
    private final SubscriptionDao subscriptionDao;

    public NotificationServiceImpl(EstateBot estateBot, SubscriptionDao subscriptionDao) {
        this.estateBot = estateBot;
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public void notifySubscribers(List<Announcement> announcements) {
        List<Subscription> subscriptions = this.subscriptionDao.getAllSubscriptions();
        subscriptions.forEach(subscription -> {
            announcements.forEach(announcement -> {
                this.estateBot.sendNotification(subscription.getChatId(), announcement);
            });
        });
    }

    @Override
    public void notifySubscribers(Announcement announcement) {
        List<Subscription> subscriptions = this.subscriptionDao.getAllSubscriptions();
        subscriptions.forEach(subscription -> {
            this.estateBot.sendNotification(subscription.getChatId(), announcement);
        });
    }
}
