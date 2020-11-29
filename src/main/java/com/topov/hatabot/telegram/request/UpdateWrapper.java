package com.topov.hatabot.telegram.request;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Wrapper class for convenient interaction with {@link org.telegram.telegrambots.meta.api.objects.Update}
 * class (Adapter-like).
 */
@Getter
public class UpdateWrapper {
    private final Update update;

    public UpdateWrapper(Update update) {
        this.update = update;
    }

    public String getUserId() { return this.update.getMessage().getFrom().getId().toString(); }

    public String getChatId() {
        return this.update.getMessage().getChatId().toString();
    }

    public boolean isCommand() {
        return this.update.getMessage().getText().startsWith("/");
    }

    public TelegramCommand unwrapCommand() {
        return new TelegramCommand(this.update);
    }

    public TelegramUpdate unwrapUpdate() {
        return new TelegramUpdate(this.update);
    }
}
