package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

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
}
