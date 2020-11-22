package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.UpdateResult;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;
    private final Map<String, CommandHandler> handlers;
    protected final MessageSource messageSource;

    protected AbstractBotState(BotStateName botStateName, MessageSource messageSource) {
        this.botStateName = botStateName;
        this.messageSource = messageSource;
        this.handlers = new HashMap<>();
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        return new UpdateResult(update.getChatId(), "I dont understand! Tap /help to see options");
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext.ChangeStateCallback changeStateCallback) {
        return this.handlers.get(command.getCommand()).act(command, changeStateCallback);
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult handleMainCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /main command");
        changeState.accept(BotStateName.INITIAL);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("initial.entrance", null, Locale.ENGLISH));
    }

    @CommandMapping(forCommand = "/help")
    public CommandResult handleHelpCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /help command");
        return new CommandResult(command.getChatId(), "Commands:\n" + getAvailableCommandsInfo());
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }

    private String getAvailableCommandsInfo() {
        final StringBuilder commands = new StringBuilder();

        this.handlers.keySet().stream()
            .map(command -> command.concat("\n"))
            .forEach(commands::append);

        return commands.toString();
    }

    public void addCommandHandler(CommandHandler commandHandler) {
        this.handlers.put(commandHandler.getCommandPath(), commandHandler);
    }
}
