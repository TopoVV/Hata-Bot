package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.handler.CommandHandler;
import com.topov.estatesearcher.telegram.state.handler.CommandInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;
    private final String headerKey;
    private final String commandsKey;
    private final Map<CommandInfo, CommandHandler> handlers;
    private final MessageSourceAdapter messageSource;

    @Setter
    private Keyboard keyboard;

    protected AbstractBotState(BotStateName botStateName, String headerKey, String commandsKey, MessageSourceAdapter messageSource) {
        this.botStateName = botStateName;
        this.headerKey = headerKey;
        this.commandsKey = commandsKey;
        this.messageSource = messageSource;
        this.handlers = new HashMap<>();
    }

    public void addCommandHandler(CommandHandler commandHandler, CommandInfo commandInfo) {
        this.handlers.put(commandInfo, commandHandler);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        return UpdateResult.withMessage("I dont understand!");
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext context) {
        if (this.handlers.containsKey(command.getCommand())) {
            return this.handlers.get(command.getCommand()).act(command, context);
        } else {
            return CommandResult.withMessage("No such command supported");
        }
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        final String header = this.messageSource.getMessage(this.headerKey, context);
        final String commands = this.messageSource.getMessage(this.commandsKey, context);
        final String entranceText = this.messageSource.getMessage("entrance.template", context, header, commands);
        final EntranceMessage entranceMessage = new EntranceMessage(context.getChatId(), entranceText, this.keyboard);
        return Optional.of(entranceMessage);
    }
}
