package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.dao.SubscriptionDao;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.provider.SubscriptionStepProvider;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.subscription.step.SubscriptionStep;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Optional;

@Log4j2
@Service
public class SubscriptionBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;
    private final SubscriptionStepProvider stepProvider;
    private final SubscriptionDao subscriptionDao;
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache,
                                SubscriptionStepProvider stepProvider,
                                SubscriptionDao subscriptionDao,
                                BotStateEvaluator stateEvaluator,
                                UpdateResultFactory updateResultFactory) {
        super(StateName.SUBSCRIPTION, stateEvaluator);
        this.subscriptionCache = subscriptionCache;
        this.stepProvider = stepProvider;
        this.subscriptionDao = subscriptionDao;
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        switch (text) {
            case "/cancel": return handleCancelCommand(chatId);
            case "/save": return handleSaveCommand(chatId);
        }

        final Optional<SubscriptionStep.StepName> currentStep = this.stepProvider.getCurrentStepName(chatId);

        if (!currentStep.isPresent()) {
            switch (text) {
                case "/max_price": return handleMaxPriceCommand(chatId);
                case "/min_price": return handleMinPriceCommand(chatId);
                case "/city": return handleCityCommand(chatId);
                default: return this.updateResultFactory.createUpdateResult("replies.global.notSupported");
            }
        }

        return delegateToStep(chatId, update, currentStep.get());
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final Optional<SubscriptionStep.StepName> currentStepName = this.stepProvider.getCurrentStepName(chatId);
        final Keyboard keyboard = new Keyboard();

        keyboard.addOneButton(new KeyboardButton("/cancel"));
        keyboard.addOneButton(new KeyboardButton("/save"));

        if (!currentStepName.isPresent()) {
            keyboard.addOneButton(new KeyboardButton("/minPrice"));
            keyboard.addOneButton(new KeyboardButton("/maxPrice"));
            keyboard.addOneButton(new KeyboardButton("/city"));
        } else {
            final SubscriptionStep subscriptionStep = this.stepProvider.getSubscriptionStep(currentStepName.get());
            keyboard.AddButtons(subscriptionStep.getKeyboardButtons(update));
        }

        return keyboard;
    }

    private UpdateResult handleMinPriceCommand(long chatId) {
        this.stepProvider.setSubscriptionStepForUser(chatId, SubscriptionStep.StepName.MIN_PRICE);
        return this.updateResultFactory.createUpdateResult("replies.subscription.minPrice");
    }

    private UpdateResult handleMaxPriceCommand(long chatId) {
        this.stepProvider.setSubscriptionStepForUser(chatId, SubscriptionStep.StepName.MAX_PRICE);
        return this.updateResultFactory.createUpdateResult("replies.subscription.maxPrice");
    }

    private UpdateResult handleCityCommand(long chatId) {
        this.stepProvider.setSubscriptionStepForUser(chatId, SubscriptionStep.StepName.CITY);
        return this.updateResultFactory.createUpdateResult("replies.subscription.city");
    }

    private UpdateResult handleSaveCommand(long chatId) {
        this.stepProvider.resetSubscriptionStepForUser(chatId);
        this.stateEvaluator.setStateForUser(chatId, StateName.INITIAL);
        final Optional<Subscription> cachedSubscription = this.subscriptionCache.getCachedSubscription(chatId);

        if (cachedSubscription.isPresent()) {
            this.subscriptionDao.saveSubscription(cachedSubscription.get());
            this.subscriptionCache.removeCachedSubscription(chatId);
            return new UpdateResult("Subscription saved");
        } else {
            return new UpdateResult("You didn't create a subscription yet");
        }
    }

    private UpdateResult handleCancelCommand(long chatId) {
        this.subscriptionCache.removeCachedSubscription(chatId);
        this.stepProvider.resetSubscriptionStepForUser(chatId);
        this.stateEvaluator.setStateForUser(chatId, StateName.INITIAL);
        return new UpdateResult("Subscription cancelled");
    }

    private UpdateResult delegateToStep(long chatId, Update update, SubscriptionStep.StepName stepName) {
        final SubscriptionStep step = this.stepProvider.getSubscriptionStep(stepName);
        final UpdateResult updateResult = step.handleSubscriptionStep(update);
        this.stepProvider.resetSubscriptionStepForUser(chatId);
        return updateResult;
    }
}
