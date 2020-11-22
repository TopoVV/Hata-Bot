package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel"),
    @AcceptedCommand(commandName = "/save"),
    @AcceptedCommand(commandName = "/minPrice"),
    @AcceptedCommand(commandName = "/maxPrice"),
    @AcceptedCommand(commandName = "/city"),
})
public class SubscriptionBotState extends AbstractSubscriptionBotState {

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache, MessageSource messageSource) {
        super(BotStateName.SUBSCRIPTION, messageSource, subscriptionCache);
    }

    @CommandMapping(forCommand = "/minPrice")
    public CommandResult handleMinPriceCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /minPrice command");
        changeState.accept(BotStateName.SUBSCRIPTION_MIN_PRICE);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("subscription.minPrice.entrance", null, Locale.ENGLISH));
    }

    @CommandMapping(forCommand = "/maxPrice")
    public CommandResult handleMaxPriceCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /maxPrice command");
        changeState.accept(BotStateName.SUBSCRIPTION_MAX_PRICE);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("subscription.maxPrice.entrance", null, Locale.ENGLISH));
    }

    @CommandMapping(forCommand = "/city")
    public CommandResult handleCityCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /city command");
        changeState.accept(BotStateName.SUBSCRIPTION_CITY);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("subscription.city.entrance", null, Locale.ENGLISH));
    }
}
