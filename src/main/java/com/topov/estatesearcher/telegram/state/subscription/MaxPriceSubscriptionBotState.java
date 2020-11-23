package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.subscription.update.MaxPriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back", description = "go back"),
    @AcceptedCommand(commandName = "/current", description = "current subscription config")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/current" }),
})
public class MaxPriceSubscriptionBotState extends AbstractBotState {
    private static final String HEADER = "Specify max price.";

    private final SubscriptionCache subscriptionCache;

    @Autowired
    public MaxPriceSubscriptionBotState(SubscriptionCache subscriptionCache) {
        super(BotStateName.SUBSCRIPTION_MAX_PRICE);
        this.subscriptionCache = subscriptionCache;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        log.debug("Handling max price update");
        final Long chatId = update.getChatId();
        final String text = update.getText();

        try {
            final int maxPrice = Integer.parseInt(text);
            this.subscriptionCache.modifySubscription(chatId, new MaxPriceUpdate(maxPrice));
            changeState.accept(BotStateName.SUBSCRIPTION);

            final String current = this.subscriptionCache.getCachedSubscription(chatId)
                .map(Subscription::toString)
                .orElse("Not yet created");

            final String template = "Current:\n%s\n\nMax price saved.";
            final String message = String.format(template, current);
            return new UpdateResult(message);
        } catch (NumberFormatException e) {
            log.error("Invalid price: {}", text);
            return new UpdateResult("Invalid price");
        }
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /back command for user {}", command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command) {
        log.info("Executing /cancel command for user {}", command.getChatId());

        final String current = this.subscriptionCache.getCachedSubscription(command.getChatId())
            .map(Subscription::toString)
            .orElse("Not created yet");

        return CommandResult.withMessage(String.format("Current:\n%s", current));
    }
}
