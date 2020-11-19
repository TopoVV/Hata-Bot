package com.topov.estatesearcher.telegram.state.subscription.handler;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.SubscriptionHandlerEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotState;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class AbstractSubscriptionHandler implements SubscriptionHandler {
    private final SubscriptionHandlerName subscriptionHandlerName;
    protected final BotStateEvaluator stateEvaluator;
    protected final SubscriptionHandlerEvaluator stepEvaluator;
    protected final SubscriptionCache subscriptionCache;
    protected final SubscriptionService subscriptionService;
    protected final UpdateResultFactory updateResultFactory;

    public AbstractSubscriptionHandler(SubscriptionHandlerName subscriptionHandlerName,
                                       BotStateEvaluator stateEvaluator,
                                       SubscriptionHandlerEvaluator stepEvaluator,
                                       SubscriptionCache subscriptionCache,
                                       SubscriptionService subscriptionService,
                                       UpdateResultFactory updateResultFactory) {
        this.subscriptionHandlerName = subscriptionHandlerName;
        this.stateEvaluator = stateEvaluator;
        this.stepEvaluator = stepEvaluator;
        this.subscriptionCache = subscriptionCache;
        this.subscriptionService = subscriptionService;
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public List<KeyboardButton> getKeyboardButtons(Update update) {
        return Collections.emptyList();
    }

    protected UpdateResult handleSaveCommand(long chatId) {
        this.stateEvaluator.setStateForUser(chatId, BotState.StateName.INITIAL);
        final Optional<Subscription> cachedSubscription = this.subscriptionCache.getCachedSubscription(chatId);

        if (cachedSubscription.isPresent()) {
            this.subscriptionService.saveSubscription(cachedSubscription.get());
            this.subscriptionCache.removeCachedSubscription(chatId);
            return this.updateResultFactory.createUpdateResult("replies.subscription.save.success", "commands.initial");
        } else {
            return this.updateResultFactory.createUpdateResult("replies.subscription.save.fail.notCreated", "commands.initial");
        }

    }

    protected UpdateResult handleCancelCommand(long chatId) {
        this.subscriptionCache.removeCachedSubscription(chatId);
        this.stateEvaluator.setStateForUser(chatId, BotState.StateName.INITIAL);
        return this.updateResultFactory.createUpdateResult("replies.subscription.cancel", "commands.initial");
    }
}
