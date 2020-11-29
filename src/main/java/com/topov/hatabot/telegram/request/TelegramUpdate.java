package com.topov.hatabot.telegram.request;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUpdate {
    private final Update update;

    public TelegramUpdate(Update update) {
        this.update = update;
    }

    public String getText() {
        return this.update.getMessage().getText();
    }
    public Long getChatId() { return this.update.getMessage().getChatId(); }
}
