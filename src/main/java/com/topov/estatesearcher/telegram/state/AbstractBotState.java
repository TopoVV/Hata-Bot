package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;
    private final Map<String, CommandHandler> handlers;

    protected AbstractBotState(BotStateName botStateName) {
        this.botStateName = botStateName;
        this.handlers = new HashMap<>();
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        return new UpdateResult("These commands are available here: " +  getAvailableCommandsInfo());
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command) {
        return this.handlers.get(command.getCommand()).act(command);
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult handleMainCommand(TelegramCommand command) {
        log.info("Executing /main command");
        return new CommandResult(BotStateName.INITIAL, "/main command executed");
    }

    @CommandMapping(forCommand = "/help")
    public CommandResult handleHelpCommand(TelegramCommand command) {
        log.info("Executing /help command");
        return new CommandResult("Commands:\n" + getAvailableCommandsInfo());
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
