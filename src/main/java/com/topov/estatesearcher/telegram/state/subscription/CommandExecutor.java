package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;

public interface CommandExecutor {
    CommandResult executeCommand(TelegramCommand command, UserContext context);
}
