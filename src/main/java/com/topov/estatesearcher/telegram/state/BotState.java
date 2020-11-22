package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotState {
    UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeStateCallback);
    CommandResult executeCommand(TelegramCommand command, UserContext.ChangeStateCallback changeStateCallback);
    Keyboard createKeyboard(Update update);
}
