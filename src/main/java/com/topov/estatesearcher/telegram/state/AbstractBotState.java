package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.handler.CommandHandler;
import com.topov.estatesearcher.telegram.state.handler.CommandInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;
    private final Map<CommandInfo, CommandHandler> handlers;

    @Setter
    private Keyboard keyboard;

    protected AbstractBotState(BotStateName botStateName) {
        this.botStateName = botStateName;
        this.handlers = new HashMap<>();
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        return new UpdateResult("I dont understand!");
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext.ChangeStateCallback changeStateCallback) {
        if (this.handlers.containsKey(command.getCommand())) {
            return this.handlers.get(command.getCommand()).act(command, changeStateCallback);
        } else {
            return CommandResult.withMessage("No such command supported");
        }
    }

    public void addCommandHandler(CommandHandler commandHandler, CommandInfo commandInfo) {
        this.handlers.put(commandInfo, commandHandler);
    }

    protected String commandsInformationString() {
        return this.handlers.keySet().stream()
            .map(CommandInfo::toString)
            .collect(Collectors.joining("\n"));
    }
}
