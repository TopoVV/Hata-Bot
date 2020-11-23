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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;
    private final Map<CommandInfo, CommandHandler> handlers;

    @Autowired
    private MessageSource messageSource;

    @Setter
    private Keyboard keyboard;

    protected AbstractBotState(BotStateName botStateName) {
        this.botStateName = botStateName;
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
        final Locale locale = new Locale("ru");
        final String headerKey = getCurrentStateHeaderKey();
        final String header = messageSource.getMessage(headerKey, null, locale);
        final String commandsInformationString = commandsInformationString(locale);
        final String entranceText = this.messageSource.getMessage("entrance.template", new Object[]{header, commandsInformationString}, locale);
        return Optional.of(new EntranceMessage(context.getChatId(), entranceText, this.keyboard));
    }

    protected String getCurrentStateHeaderKey() {
        return "main.header";
    }

    private String commandsInformationString(Locale locale) {
        return this.handlers.keySet().stream()
            .map(commandInfo -> {
                final StringBuilder info = new StringBuilder(commandInfo.getCommandName() + " - ");
                final String desc = this.messageSource.getMessage(commandInfo.getDescription(), null, locale);
                return info.append(desc).toString();
            })
            .collect(Collectors.joining("\n"));
    }
}
