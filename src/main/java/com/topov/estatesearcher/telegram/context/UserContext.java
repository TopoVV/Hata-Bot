package com.topov.estatesearcher.telegram.context;

import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Context object of the State design pattern.
 */
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

    public CommandResult executeCommand(TelegramCommand command, BotState state) {
        return state.executeCommand(command, this);
    }

    public void changeState(BotStateName newState) {
        this.currentStateName = newState;
    }

    public UpdateResult handleUpdate(TelegramUpdate update, BotState state) {
        return state.handleUpdate(update, this);
    }

    public String getEntranceMessage(BotState currentState, UpdateWrapper update) {
        return currentState.getEntranceMessage(update);
    }
}
