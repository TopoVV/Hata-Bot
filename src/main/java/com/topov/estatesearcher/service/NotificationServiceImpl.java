package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.EstateBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final EstateBot estateBot;
    private final SubscriptionDao subscriptionDao;

    @Autowired
    public NotificationServiceImpl(EstateBot estateBot, SubscriptionDao subscriptionDao) {
        this.estateBot = estateBot;
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public void notifySubscribers(List<Announcement> announcements) {
        Collection<Subscription> subscriptions = this.subscriptionDao.getAllSubscriptions();
        subscriptions.forEach(subscription -> {
            announcements.forEach(announcement -> {
                this.estateBot.sendNotification(Long.valueOf(subscription.getChatId()), announcement);
            });
        });
    }

    @Override
    public void notifySubscribers(Announcement announcement) {
        Collection<Subscription> subscriptions = this.subscriptionDao.getAllSubscriptions();
        subscriptions.forEach(subscription -> {
            this.estateBot.sendNotification(Long.valueOf(subscription.getChatId()), announcement);
        });
    }
}
