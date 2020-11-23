package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

public interface BotState {
    UpdateResult handleUpdate(TelegramUpdate update, UserContext context);
    CommandResult executeCommand(TelegramCommand command, UserContext context);
    String getEntranceMessage(UpdateWrapper update);
    Keyboard getKeyboard();
}
