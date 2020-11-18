package com.topov.estatesearcher.telegram.state.subscription.step;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.update.MaxPriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class MaxPriceSubscriptionStep extends AbstractSubscriptionStep {
    private final SubscriptionCache subscriptionCache;
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    public MaxPriceSubscriptionStep(SubscriptionCache subscriptionCache, UpdateResultFactory updateResultFactory) {
        super(StepName.MAX_PRICE);
        this.subscriptionCache = subscriptionCache;
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final java.lang.String text = update.getMessage().getText();

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
