package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import lombok.extern.log4j.Log4j2;
@Log4j2
public abstract class AbstractSubscriptionBotState extends AbstractBotState {
    protected final SubscriptionCache subscriptionCache;

    protected AbstractSubscriptionBotState(BotStateName botStateName, SubscriptionCache subscriptionCache) {
        super(botStateName);
        this.subscriptionCache = subscriptionCache;
    }

    @CommandMapping(forCommand = "/save")
    public UpdateResult handleSaveCommand(TelegramCommand command) {
        log.info("Executing /save command");
        return new UpdateResult("/save command executed");
    }

    @CommandMapping(forCommand = "/cancel")
    public UpdateResult handleCancelCommand(TelegramCommand command) {
        log.info("Executing /cancel command");
        return new UpdateResult("/cancel command executed");
    }

}
