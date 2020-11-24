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
import com.topov.estatesearcher.telegram.state.StateUtils;
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
    @AcceptedCommand(commandName = "/current"),
    @AcceptedCommand(commandName = "/language"),
    @AcceptedCommand(commandName = "/donate")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/minPrice", "/maxPrice", "/city", "/current" }),
    @KeyboardRow(buttons = { "/cancel", "/save", "/main" }),
    @KeyboardRow(buttons = { "/language", "/donate" })
})
public class SubscribeBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;

    @Autowired
    public SubscribeBotState(SubscriptionCache subscriptionCache, MessageSourceAdapter messageSource) {
        super(StateUtils.SUBSCRIBE_PROPS, messageSource);
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
            final String message = getMessage("subscribe.save.success.reply", context);
            return CommandResult.withMessage(message);
        }

        final String message = getMessage("subscribe.save.notCreated.reply", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult onCancel(TelegramCommand command, UserContext context) {
        log.info("Executing /cancel command for user {}", context.getChatId());
        this.subscriptionCache.evictCache(context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        final String message = getMessage("subscribe.cancel.success.reply", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getChatId());

        final String current = this.subscriptionCache.getCachedSubscription(context.getChatId())
            .map(Subscription::toString)
            .orElse(getMessage("subscribe.current.notCreated.reply", context));

        final String message = getMessage("subscribe.current.success.reply", context, current);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/language" )
    public CommandResult onLanguage(TelegramCommand command, UserContext context) {
        log.info("Executing /language command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        log.info("Executing /donate command for user: {}", context.getChatId());
        context.setCurrentStateName(BotStateName.DONATE);
        final String message = getMessage("subscribe.donate.reply", context);
        return CommandResult.withMessage(message);
    }
}
