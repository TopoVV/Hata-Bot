package com.topov.estatesearcher.telegram.request;

import com.topov.estatesearcher.telegram.command.CommandInfo;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramCommand {
    private final Update update;

    public TelegramCommand(Update update) {
        this.update = update;
    }

    public CommandInfo getCommand() {
        final String text = this.update.getMessage().getText();
        return new CommandInfo(text);
    }
    public Long getChatId() { return this.update.getMessage().getChatId(); }
    public boolean isStart() {
        return this.update.getMessage().getText().equals("/start");
    }
}
