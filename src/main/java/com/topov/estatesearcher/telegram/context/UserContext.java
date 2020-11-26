package com.topov.estatesearcher.telegram.context;

import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Optional;

/**
 * Context object of the State design pattern.
 */
@Getter
@Setter
public class UserContext {
    private final String chatId;
    private Locale locale;
    private BotStateName currentStateName;
    private SubscriptionConfig subscriptionConfig;

    public UserContext(String chatId, BotStateName currentStateName) {
        this.chatId = chatId;
        this.currentStateName = currentStateName;
        this.locale = new Locale("en");
        this.subscriptionConfig = new SubscriptionConfig(chatId);
    }

    public UserContext(UserContext context) {
        this.chatId = context.getChatId();
        this.currentStateName = context.getCurrentStateName();
        this.locale = context.locale;
        this.subscriptionConfig = context.subscriptionConfig;
    }

    public CommandResult executeCommand(TelegramCommand command, BotState state) {
        return state.executeCommand(command, this);
    }

    public UpdateResult handleUpdate(TelegramUpdate update, BotState state) {
        return state.handleUpdate(update, this);
    }

    public Optional<EntranceMessage> getEntranceMessage(BotState currentState, UpdateWrapper update) {
        return currentState.getEntranceMessage(update, this);
    }

    public void resetSubscriptionConfig() {
        this.subscriptionConfig = new SubscriptionConfig(this.chatId);
    }
}
