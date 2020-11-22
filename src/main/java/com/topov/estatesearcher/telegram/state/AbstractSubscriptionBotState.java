package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;

@Log4j2
public abstract class AbstractSubscriptionBotState extends AbstractBotState {
    protected final SubscriptionCache subscriptionCache;

    protected AbstractSubscriptionBotState(BotStateName botStateName,
                                           MessageSource messageSource,
                                           SubscriptionCache subscriptionCache) {
        super(botStateName, messageSource);
        this.subscriptionCache = subscriptionCache;
    }

    @CommandMapping(forCommand = "/save")
    public CommandResult handleSaveCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /save command");
        return new CommandResult(command.getChatId(), "/save command executed");
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult handleCancelCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /cancel command");
        return new CommandResult(command.getChatId(), "/cancel command executed");
    }

}
