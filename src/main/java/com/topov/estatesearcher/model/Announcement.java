package com.topov.estatesearcher.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Announcement {
    private final String url;
    private final Integer price;
    private final LocalDateTime extractionDateTime;
    private final String description;
    private final String cityName;

    public Announcement(String url, Integer price, LocalDateTime extractionDateTime, String description, String cityName) {
        this.url = url;
        this.price = price;
        this.extractionDateTime = extractionDateTime;
        this.description = description;
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return String.format("City: %s\nURL: %s\nPrice: %d\n\nDescription: %s", cityName, url, price, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Announcement that = (Announcement) o;
        return price.equals(that.price) &&
            description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, description);
    }
}
