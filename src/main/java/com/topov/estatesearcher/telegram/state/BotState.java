package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.Hint;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface BotState {
    UpdateResult handleUpdate(Update update);
    Hint getHint(Update update);
    Keyboard getKeyboard();

    enum StateName {
        INITIAL,
        SUBSCRIPTION
    }
}
