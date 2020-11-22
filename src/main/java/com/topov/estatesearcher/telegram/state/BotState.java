package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotState {
    UpdateResult handleUpdate(Update update);
    CommandResult executeCommand(TelegramCommand command);
    Keyboard createKeyboard(Update update);
    String getEntranceMessage();
}
