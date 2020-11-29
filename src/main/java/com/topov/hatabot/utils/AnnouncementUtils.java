package com.topov.hatabot.utils;

import com.topov.hatabot.model.Announcement;
import com.topov.hatabot.model.City;
import com.topov.hatabot.model.Subscription;

public class AnnouncementUtils {
    public static boolean announcementMatchesSubscription(Announcement announcement, Subscription subscription) {
        final boolean cityMatches = subscription.getCity()
            .map(City::getCityName)
            .map(subscribedCity -> announcement.getCityName().equals(subscribedCity))
            .orElse(true);

        final boolean maxPriceMatches = subscription.getMaxPrice() > announcement.getPrice();
        final boolean minPriceMatches = subscription.getMinPrice() < announcement.getPrice();

        return  cityMatches && maxPriceMatches && minPriceMatches;
    }
}
