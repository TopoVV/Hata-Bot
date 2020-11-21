package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
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

    protected final BotStateEvaluator stateEvaluator;

    protected AbstractBotState(BotStateName botStateName, BotStateEvaluator stateEvaluator) {
        this.botStateName = botStateName;
        this.handlers = new HashMap<>();
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        return new UpdateResult("These commands are available here: " +  getAvailableCommandsInfo());
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        return this.handlers.get(command).act(update);
    }

    @CommandMapping(forCommand = "/main")
    public UpdateResult handleMainCommand(Update update) {
        log.info("Executing /main command");
        if (botStateName.equals(BotStateName.INITIAL)) {
            return new UpdateResult("Commands:\n" + getAvailableCommandsInfo());
        }
        final long chatId = update.getMessage().getChatId();
        changeState(chatId, BotStateName.INITIAL);
        return new UpdateResult("/main command executed");
    }

    @CommandMapping(forCommand = "/help")
    public UpdateResult handleHelpCommand(Update update) {
        log.info("Executing /help command");
        return new UpdateResult("Commands:\n" + getAvailableCommandsInfo());
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
        this.handlers.put(commandHandler.getCommand(), commandHandler);
    }

    protected void changeState(long chatId, BotStateName newState) {
        this.stateEvaluator.setStateForUser(chatId, newState);
    }
}
