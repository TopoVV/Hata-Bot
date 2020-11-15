package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdateHandler {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard getKeyboard(Update update);
}
