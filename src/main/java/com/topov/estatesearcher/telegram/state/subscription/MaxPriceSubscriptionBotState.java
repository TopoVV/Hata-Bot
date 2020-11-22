package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.TelegramUpdate;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.subscription.update.MaxPriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel")
})
public class MaxPriceSubscriptionBotState extends AbstractSubscriptionBotState {

    @Autowired
    public MaxPriceSubscriptionBotState(SubscriptionCache subscriptionCache, MessageSource messageSource) {
        super(BotStateName.SUBSCRIPTION_MAX_PRICE, messageSource, subscriptionCache);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        log.debug("Handling price update");
        final Long chatId = update.getChatId();
        final String text = update.getText();

        try {
            final int maxPrice = Integer.parseInt(text);
            this.subscriptionCache.modifySubscription(chatId, new MaxPriceUpdate(maxPrice));
            return new UpdateResult(chatId, "Done");
        } catch (NumberFormatException e) {
            log.error("Invalid city: {}", text);
            return new UpdateResult(chatId, "Invalid price");
        }
    }
}
