package com.topov.estatesearcher.telegram.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Notification {
    private final String userId;
    private final String text;

    public Notification(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }
}
