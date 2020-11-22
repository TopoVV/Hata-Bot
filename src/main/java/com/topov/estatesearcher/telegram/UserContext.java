package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import lombok.Getter;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
public class UserContext {
    private final Long chatId;
    private BotStateName currentStateName;

    public UserContext(Long chatId, BotStateName currentStateName) {
        this.chatId = chatId;
        this.currentStateName = currentStateName;
    }

    public UserContext(UserContext context) {
        this.chatId = context.getChatId();
        this.currentStateName = context.getCurrentStateName();
    }

    public CommandResult executeCommand(TelegramCommand command, Map<BotStateName, BotState> states) {
        final CommandResult commandResult = states.get(this.currentStateName).executeCommand(command);
        commandResult.changedState().ifPresent(this::changeState);
        return commandResult;
    }

    public String getCurrentStateEntranceMessage(Map<BotStateName, BotState> states) {
        return states.get(this.currentStateName).getEntranceMessage();
    }

    protected void changeState(BotStateName newState) {
        this.currentStateName = newState;
    }

}
