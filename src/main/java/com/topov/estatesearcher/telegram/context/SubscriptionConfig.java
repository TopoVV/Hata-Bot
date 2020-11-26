package com.topov.estatesearcher.telegram.context;

import com.topov.estatesearcher.model.City;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Optional;

@Getter
@Setter
public class SubscriptionConfig {
    private final String chatId;
    private Integer minPrice;
    private Integer maxPrice;
    private City city;

    public SubscriptionConfig(String chatId) {
        this.chatId = chatId;
    }

    public boolean isConfigured() {
        return this.minPrice != null ||
            this.maxPrice != null ||
            this.city != null;
    }

    public String toString(MessageFormat messageFormat) {
        final String minPrice = this.minPrice == null ? "-" : this.minPrice.toString();
        final String maxPrice = this.maxPrice == null ? "-" : this.maxPrice.toString();
        final String cityName = this.city == null ? "-" : this.city.getCityName();
        return messageFormat.format(new Object[] { minPrice, maxPrice, cityName });
    }

    public  Optional<City> getCity() {
        return Optional.of(this.city);
    }
}
