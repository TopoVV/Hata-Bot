package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdateHandler {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard getKeyboard(Update update);
}
