package com.topov.hatabot.telegram.state;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.Keyboard;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.request.UpdateWrapper;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.EntranceMessage;
import com.topov.hatabot.telegram.result.UpdateResult;

import java.util.Optional;

public interface BotState {
    UpdateResult handleUpdate(TelegramUpdate update, UserContext context);
    CommandResult executeCommand(TelegramCommand command, UserContext context);
    Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context);
    Keyboard getKeyboard();
}
