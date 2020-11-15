package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.UserBotStateEvaluator;
import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionStep;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
public class SubscriptionBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;
    private final SubscriptionStepProvider stepProvider;
    private final SubscriptionStorage subscriptionStorage;
    private final UserBotStateEvaluator stateEvaluator;

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache,
                                SubscriptionStepProvider stepProvider,
                                SubscriptionStorage subscriptionStorage,
                                UserBotStateEvaluator stateEvaluator) {
        super(StateName.SUBSCRIPTION);
        this.subscriptionCache = subscriptionCache;
        this.stepProvider = stepProvider;
        this.subscriptionStorage = subscriptionStorage;
        this.stateEvaluator = stateEvaluator;
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
                case "/price": return handlePriceCommand(chatId);
                case "/city": return new UpdateResult("Not implemented yet");
                default: return new UpdateResult("Command not supported");
            }
        }

        return delegateToStep(chatId, update, currentStep.get());
    }

    @Override
    public Hint getHint(Update update) {
        final Hint hint = new Hint("\n/save - save subscription\n/cancel - cancel subscription");
        final Long chatId = update.getMessage().getChatId();

        final Optional<SubscriptionStep.StepName> currentStep = this.stepProvider.getCurrentStepName(chatId);

        if (!currentStep.isPresent()) {
            hint.appendMessage("/price - subscribe for price");
        } else {
            final String message = currentStep.map(stepProvider::getSubscriptionStep)
                .map(SubscriptionStep::getHint)
                .map(Hint::getMessage)
                .orElse("");

            hint.appendMessage(message);
        }
        return hint;
    }

    @Override
    public Keyboard getKeyboard() {
        final KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(new KeyboardButton("/price"));
        keyboardButtons.add(new KeyboardButton("/save"));
        keyboardButtons.add(new KeyboardButton("/cancel"));
        return new Keyboard(Collections.singletonList(keyboardButtons));
    }

    private UpdateResult handlePriceCommand(long chatId) {
        this.stepProvider.setSubscriptionStepForUser(chatId, SubscriptionStep.StepName.PRICE);
        return new UpdateResult("Specifying the subscription price");
    }

    private UpdateResult handleSaveCommand(long chatId) {
        this.stepProvider.resetSubscriptionStepForUser(chatId);
        this.stateEvaluator.setStateForUser(chatId, StateName.INITIAL);
        final Optional<Subscription> cachedSubscription = subscriptionCache.getCachedSubscription(chatId);
        if (cachedSubscription.isPresent()) {
            this.subscriptionStorage.saveSubscription(chatId, cachedSubscription.get());
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
