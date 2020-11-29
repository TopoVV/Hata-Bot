package com.topov.estatesearcher.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class Announcement {
    private Long announcementId;
    private String url;
    private Integer price;
    private LocalDateTime extractionDateTime;
    private String description;
    private String cityName;

    public Announcement(String url, Integer price, LocalDateTime extractionDateTime, String description, String cityName) {
        this.url = url;
        this.price = price;
        this.extractionDateTime = extractionDateTime;
        this.description = description;
        this.cityName = cityName;
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
