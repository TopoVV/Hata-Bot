package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotState {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard createKeyboard(Update update);

    enum StateName {
        INITIAL,
        SUBSCRIPTION
    }
}
