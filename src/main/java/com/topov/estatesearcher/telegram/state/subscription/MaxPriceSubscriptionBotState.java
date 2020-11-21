package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

@Log4j2
@Service
public class MaxPriceSubscriptionBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;
    private final SubscriptionCache subscriptionCache;

    @Autowired
    public MaxPriceSubscriptionBotState(BotStateEvaluator stateEvaluator, SubscriptionCache subscriptionCache) {
        super(BotStateName.SUBSCRIPTION_MAX_PRICE);
        this.subscriptionCache = subscriptionCache;
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();


        return new UpdateResult("SUBSCRIPTION MAX PRICE BOT STATE");
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        final long chatId = update.getMessage().getChatId();

        switch (command) {
            case "/cancel": this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION); break;
            case "/main": this.stateEvaluator.setStateForUser(chatId, BotStateName.INITIAL); break;
        }

        return new UpdateResult("Command executed");
    }

//    private UpdateResult handleMaxPriceUpdate(Update update) {
//        final Long chatId = update.getMessage().getChatId();
//        final String text = update.getMessage().getText();
//
//        try {
//            int newMaxPrice = Integer.parseInt(text);
//            this.subscriptionCache.modifySubscription(chatId, new MaxPriceUpdate(newMaxPrice));
//            return this.updateResultFactory.createUpdateResult("replies.subscription.update.success");
//        } catch (NumberFormatException e) {
//            log.error("Invalid number format: {}", text, e);
//            return this.updateResultFactory.createUpdateResult("replies.subscription.update.price.fail.invalidInput", new Object[] { text });
//        }
//    }
}
