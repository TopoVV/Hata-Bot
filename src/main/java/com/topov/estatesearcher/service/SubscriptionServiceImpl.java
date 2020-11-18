package com.topov.estatesearcher.service;

import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public void saveSubscription(long chatId, Subscription subscription) {
        subscriptionDao.saveSubscription(chatId, subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptionsForUser(long chatId) {
        return subscriptionDao.getAllSubscriptions();
    }
}
