package com.topov.estatesearcher.telegram.state.subscription.steps;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.SubscriptionCache;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class SubscriptionPriceStep extends AbstractSubscriptionStep {
    private final SubscriptionCache subscriptionCache;

    public SubscriptionPriceStep(SubscriptionCache subscriptionCache) {
        super(StepName.PRICE);
        this.subscriptionCache = subscriptionCache;
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        try {
            int price = Integer.parseInt(text);
            this.subscriptionCache.modifySubscription(chatId, new PriceUpdator(price));
            return new UpdateResult("Subscription updated");
        } catch (NumberFormatException e) {
            log.error("Invalid number format: {}", text, e);
            return new UpdateResult("Invalid price");
        }
    }

    @Override
    public Hint getHint() {
        return new Hint("\nPlease, specify price\n");
    }
}
