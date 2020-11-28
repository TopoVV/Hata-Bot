package com.topov.estatesearcher.utils;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.model.Subscription;

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
