package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel")
})
public class MaxPriceSubscriptionBotState extends AbstractSubscriptionBotState {

    @Autowired
    public MaxPriceSubscriptionBotState(SubscriptionCache subscriptionCache) {
        super(BotStateName.SUBSCRIPTION_MAX_PRICE, subscriptionCache);
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();


        return new UpdateResult("SUBSCRIPTION MAX PRICE BOT STATE");
    }

    @Override
    public String getEntranceMessage() {
        return "SUBSCRIPTION MAX PRICE BOT STATE";
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
