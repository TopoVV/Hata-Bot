package com.topov.hatabot.telegram.notification;

import lombok.Getter;

@Getter
public class Notification {
    private final String userId;
    private final String text;

    public Notification(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }
}
