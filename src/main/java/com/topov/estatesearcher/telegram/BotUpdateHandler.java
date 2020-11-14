package com.topov.estatesearcher.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface BotUpdateHandler {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard getKeyboard(Update update);
}
