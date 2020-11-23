package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main", description = "main.description"),
    @AcceptedCommand(commandName = "/cancel", description = "cancel.description"),
    @AcceptedCommand(commandName = "/save", description = "save.description"),
    @AcceptedCommand(commandName = "/minPrice", description = "minPrice.description"),
    @AcceptedCommand(commandName = "/maxPrice", description = "maxPrice.description"),
    @AcceptedCommand(commandName = "/city", description = "city.description"),
    @AcceptedCommand(commandName = "/current", description = "current.description")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/minPrice", "/maxPrice", "/city" }),
    @KeyboardRow(buttons = { "/cancel", "/save", "/main" }),
    @KeyboardRow(buttons = { "/current" })
})
public class SubscriptionBotState extends AbstractBotState {
    private static final String HEADER = "Here you can subscribe.";

    private final SubscriptionCache subscriptionCache;

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache) {
        super(BotStateName.SUBSCRIPTION);
        this.subscriptionCache = subscriptionCache;
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        final String entranceText = String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
        return Optional.of(new EntranceMessage(context.getChatId(), entranceText, this.getKeyboard()));
    }

    @CommandMapping(forCommand = "/minPrice")
    public CommandResult onMinPrice(TelegramCommand command, UserContext context) {
        log.info("Executing /minPrice command for user {}", context.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION_MIN_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/maxPrice")
    public CommandResult onMaxPrice(TelegramCommand command, UserContext context) {
        log.info("Executing /maxPrice command for user {}", context.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION_MAX_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/city")
    public CommandResult onCity(TelegramCommand command, UserContext context) {
        log.info("Executing /city command for user {}", context.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION_CITY);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getChatId());
        this.subscriptionCache.evictCache(context.getChatId());
        context.changeState(BotStateName.MAIN);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/save")
    public CommandResult onSave(TelegramCommand command, UserContext context) {
        log.info("Executing /save command for user {}", context.getChatId());
        if (this.subscriptionCache.flush(context.getChatId())) {
            return CommandResult.withMessage("The subscription saved.");
        }

        return CommandResult.withMessage("You didnt create any new subscriptions.");
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult onCancel(TelegramCommand command, UserContext context) {
        log.info("Executing /cancel command for user {}", context.getChatId());
        this.subscriptionCache.evictCache(context.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION);
        return CommandResult.withMessage("Subscription canceled.");
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getChatId());

        final String current = this.subscriptionCache.getCachedSubscription(context.getChatId())
            .map(Subscription::toString)
            .orElse("Not created yet");

        return CommandResult.withMessage(String.format("Current:\n%s", current));
    }
}
