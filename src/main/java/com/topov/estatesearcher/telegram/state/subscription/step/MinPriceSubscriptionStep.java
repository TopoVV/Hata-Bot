package com.topov.estatesearcher.telegram.state.subscription.step;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.state.subscription.update.MinPriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class MinPriceSubscriptionStep extends AbstractSubscriptionStep {
    private final SubscriptionCache subscriptionCache;

    public MinPriceSubscriptionStep(SubscriptionCache subscriptionCache) {
        super(StepName.MIN_PRICE);
        this.subscriptionCache = subscriptionCache;
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final java.lang.String text = update.getMessage().getText();

        try {
            int price = Integer.parseInt(text);
            this.subscriptionCache.modifySubscription(chatId, new MinPriceUpdate(price));
            return new UpdateResult("Subscription updated");
        } catch (NumberFormatException e) {
            log.error("Invalid number format: {}", text, e);
            return new UpdateResult("Invalid price");
        }
    }

    @Override
    public String getHintMessage() {
        return "\nPlease, specify min price\n";
    }
}
