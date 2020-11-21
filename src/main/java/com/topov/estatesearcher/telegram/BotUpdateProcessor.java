package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdateProcessor {
    UpdateResult processUpdate(Update update);
    UpdateResult processFirstInteraction(long chatId);
    Keyboard getKeyboard(Update update);

}
