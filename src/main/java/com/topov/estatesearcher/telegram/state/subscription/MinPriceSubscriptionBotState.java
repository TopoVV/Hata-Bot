package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.provider.CommandExecutorProvider;
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
public class MinPriceSubscriptionBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;
    private final SubscriptionCache subscriptionCache;
    private final CommandExecutorProvider executorProvider;

    @Autowired
    public MinPriceSubscriptionBotState(BotStateEvaluator stateEvaluator,
                                        SubscriptionCache subscriptionCache,
                                        CommandExecutorProvider executorProvider) {
        super(BotStateName.SUBSCRIPTION_MIN_PRICE);
        this.stateEvaluator = stateEvaluator;
        this.subscriptionCache = subscriptionCache;
        this.executorProvider = executorProvider;
        this.supportedCommands = Collections.singletonList("/subscriptions");
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling price update");
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        if (isSupportedCommand(text)) {
            this.executorProvider.getExecutor(text).ifPresent(executor -> executor.execute(update));
        }

        return new UpdateResult("SUBSCRIPTION MIN PRICE BOT STATE");
    }

//    private UpdateResult handleMinPriceUpdate(Update update) {
//        final Long chatId = update.getMessage().getChatId();
//        final String text = update.getMessage().getText();
//
//        try {
//            int price = Integer.parseInt(text);
//            this.subscriptionCache.modifySubscription(chatId, new MinPriceUpdate(price));
//            return this.updateResultFactory.createUpdateResult("replies.subscription.update.success");
//        } catch (NumberFormatException e) {
//            log.error("Invalid number format: {}", text, e);
//            return this.updateResultFactory.createUpdateResult("replies.subscription.update.price.fail.invalidInput", new Object[] { text });
//        }
//    }

}