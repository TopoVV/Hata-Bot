package com.topov.estatesearcher.telegram.request;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramCommand {
    private final Update update;

    public TelegramCommand(Update update) {
        this.update = update;
    }

    public String getCommand() {
        return this.update.getMessage().getText();
    }
    public Long getChatId() { return this.update.getMessage().getChatId(); }
    public boolean isStart() {
        return this.update.getMessage().getText().equals("/start");
    }
}
