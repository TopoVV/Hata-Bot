package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
public abstract class AbstractSubscriptionBotState extends AbstractBotState {
    protected final SubscriptionCache subscriptionCache;

    protected AbstractSubscriptionBotState(BotStateName botStateName, BotStateEvaluator stateEvaluator, SubscriptionCache subscriptionCache) {
        super(botStateName, stateEvaluator);
        this.subscriptionCache = subscriptionCache;
    }

    @CommandMapping(forCommand = "/save")
    public UpdateResult handleSaveCommand(Update update) {
        log.info("Executing /save command");
        return new UpdateResult("/save command executed");
    }

    @CommandMapping(forCommand = "/cancel")
    public UpdateResult handleCancelCommand(Update update) {
        log.info("Executing /cancel command");
        return new UpdateResult("/cancel command executed");
    }

}
