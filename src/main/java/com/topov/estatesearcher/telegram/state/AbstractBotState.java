package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.initial.CommandAction;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;

    protected Map<String, CommandAction> actions;

    protected AbstractBotState(BotStateName botStateName) {
        this.botStateName = botStateName;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        return new UpdateResult("These commands are available here: " +  getAvailableCommandsInfo());
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        return new UpdateResult("These commands are available here: " +  getAvailableCommandsInfo());
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }

    private String getAvailableCommandsInfo() {
        final StringBuilder commands = new StringBuilder();

        this.actions.keySet().stream()
            .map(command -> command.concat("\n"))
            .forEach(commands::append);

        return commands.toString();
    }
}
