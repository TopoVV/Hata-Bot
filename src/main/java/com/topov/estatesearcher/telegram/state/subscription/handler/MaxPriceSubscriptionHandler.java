package com.topov.estatesearcher.telegram.state.subscription.handler;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.SubscriptionHandlerEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.update.MaxPriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class MaxPriceSubscriptionHandler extends AbstractSubscriptionHandler {

    @Autowired
    public MaxPriceSubscriptionHandler(BotStateEvaluator stateEvaluator,
                                       SubscriptionCache subscriptionCache,
                                       SubscriptionService subscriptionService,
                                       SubscriptionHandlerEvaluator stepEvaluator,
                                       UpdateResultFactory updateResultFactory) {
        super(SubscriptionHandlerName.MAX_PRICE, stateEvaluator, stepEvaluator, subscriptionCache, subscriptionService, updateResultFactory);
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        switch (text) {
            case "/cancel": return this.handleCancelCommand(chatId);
            case "/save": return this.handleSaveCommand(chatId);
            default: return this.handleMaxPriceUpdate(update);
        }
    }

    private UpdateResult handleMaxPriceUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        try {
            int newMaxPrice = Integer.parseInt(text);
            this.subscriptionCache.modifySubscription(chatId, new MaxPriceUpdate(newMaxPrice));
            return this.updateResultFactory.createUpdateResult("replies.subscription.update.success");
        } catch (NumberFormatException e) {
            log.error("Invalid number format: {}", text, e);
            return this.updateResultFactory.createUpdateResult("replies.subscription.update.price.fail.invalidInput", new Object[] { text });
        }
    }
}
