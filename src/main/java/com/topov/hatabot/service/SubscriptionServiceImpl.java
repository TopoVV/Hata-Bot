package com.topov.hatabot.service;

import com.topov.hatabot.dao.SubscriptionDao;
import com.topov.hatabot.model.Subscription;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Subscription> getUserSubscriptions(String userId) {
        return this.subscriptionDao.getAllUserSubscriptions(userId);
    }

    @Override
    public Optional<Subscription> findSubscription(long subscriptionId, String userId) {
        return this.subscriptionDao.findSubscription(subscriptionId, userId);
    }

    @Override
    public void removeSubscription(Long subscriptionId) {
        this.subscriptionDao.deleteSubscription(subscriptionId);
    }
}
