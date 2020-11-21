package com.topov.estatesearcher.telegram;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class UpdateWrapper {
    private final Update update;

    public UpdateWrapper(Update update) {
        this.update = update;
    }

    public Long getChatId() {
        return update.getMessage().getChatId();
    }

    public boolean isCommand() {
        return this.update.getMessage().getText().startsWith("/")
    }
}
