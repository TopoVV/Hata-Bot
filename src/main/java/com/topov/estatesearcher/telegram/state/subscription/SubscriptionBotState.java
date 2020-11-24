package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/cancel"),
    @AcceptedCommand(commandName = "/save"),
    @AcceptedCommand(commandName = "/minPrice"),
    @AcceptedCommand(commandName = "/maxPrice"),
    @AcceptedCommand(commandName = "/city"),
    @AcceptedCommand(commandName = "/current")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/minPrice", "/maxPrice", "/city" }),
    @KeyboardRow(buttons = { "/cancel", "/save", "/main" }),
    @KeyboardRow(buttons = { "/current" })
})
public class SubscriptionBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache, MessageSourceAdapter messageSource) {
        super(BotStateName.SUBSCRIPTION, "subscription.header", "subscribe.commands", messageSource);
        this.subscriptionCache = subscriptionCache;
    }

    @CommandMapping(forCommand = "/minPrice")
    public CommandResult onMinPrice(TelegramCommand command, UserContext context) {
        log.info("Executing /minPrice command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MIN_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/maxPrice")
    public CommandResult onMaxPrice(TelegramCommand command, UserContext context) {
        log.info("Executing /maxPrice command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MAX_PRICE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/city")
    public CommandResult onCity(TelegramCommand command, UserContext context) {
        log.info("Executing /city command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_CITY);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getChatId());
        this.subscriptionCache.evictCache(context.getChatId());
        context.setCurrentStateName(BotStateName.MAIN);
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
        context.setCurrentStateName(BotStateName.SUBSCRIPTION);
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
