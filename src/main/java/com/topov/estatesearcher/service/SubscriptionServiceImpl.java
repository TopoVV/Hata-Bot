package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        this.subscriptionDao.saveSubscription(subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptionsForUser(String chatId) {
        return this.subscriptionDao.getAllUserSubscriptions(chatId);
    }

    @Override
    public Optional<Subscription> findSubscription(long subscriptionId, String chatId) {
        return this.subscriptionDao.findSubscription(subscriptionId, chatId);
    }

    @Override
    public void removeSubscription(Long subscriptionId) {
        this.subscriptionDao.deleteSubscription(subscriptionId);
    }

    @Override
    public String getUserSubscriptionsInfo(String chatId) {
        return this.subscriptionDao.getAllUserSubscriptions(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }

}
