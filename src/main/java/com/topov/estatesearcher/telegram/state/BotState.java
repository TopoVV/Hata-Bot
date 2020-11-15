package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotState {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard getKeyboard();

    enum StateName {
        INITIAL,
        SUBSCRIPTION
    }
}
