package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
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

import java.util.function.Consumer;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main", description = "go to main menu"),
    @AcceptedCommand(commandName = "/cancel", description = "cancel subscription"),
    @AcceptedCommand(commandName = "/save", description = "save the subscription"),
    @AcceptedCommand(commandName = "/minPrice", description = "specify min price"),
    @AcceptedCommand(commandName = "/maxPrice", description = "specify max price"),
    @AcceptedCommand(commandName = "/city", description = "specify city"),
    @AcceptedCommand(commandName = "/current", description = "current subscription config")
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
    public String getEntranceMessage(UpdateWrapper update) {
        return String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
    }

    @CommandMapping(forCommand = "/minPrice")
    public CommandResult onMinPrice(TelegramCommand command, Consumer<BotStateName> changeState) {
        log.info("Executing /minPrice command for user {}", command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION_MIN_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/maxPrice")
    public CommandResult onMaxPrice(TelegramCommand command, Consumer<BotStateName> changeState) {
        log.info("Executing /maxPrice command for user {}", command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION_MAX_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/city")
    public CommandResult onCity(TelegramCommand command, Consumer<BotStateName> changeState) {
        log.info("Executing /city command for user {}", command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION_CITY);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, Consumer<BotStateName> changeState) {
        log.info("Executing /main command for user {}", command.getChatId());
        this.subscriptionCache.evictCache(command.getChatId());
        changeState.accept(BotStateName.INITIAL);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/save")
    public CommandResult onSave(TelegramCommand command) {
        log.info("Executing /save command for user {}", command.getChatId());
        if (this.subscriptionCache.flush(command.getChatId())) {
            return CommandResult.withMessage("The subscription saved.");
        }

        return CommandResult.withMessage("You didnt create any new subscriptions.");
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult onCancel(TelegramCommand command, Consumer<BotStateName> changeState) {
        log.info("Executing /cancel command for user {}", command.getChatId());
        this.subscriptionCache.evictCache(command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION);
        return CommandResult.withMessage("Subscription canceled.");
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command) {
        log.info("Executing /current command for user {}", command.getChatId());

        final String current = this.subscriptionCache.getCachedSubscription(command.getChatId())
            .map(Subscription::toString)
            .orElse("Not created yet");

        return CommandResult.withMessage(String.format("Current:\n%s", current));
    }
}
