package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.SubscriptionHandlerEvaluator;
import com.topov.estatesearcher.telegram.provider.SubscriptionHandlerProvider;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandler;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandlerName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Optional;

@Log4j2
@Service
public class SubscriptionBotState extends AbstractBotState {
    private final SubscriptionHandlerProvider stepProvider;
    private final UpdateResultFactory updateResultFactory;
    private final SubscriptionHandlerEvaluator handlerEvaluator;

    @Autowired
    public SubscriptionBotState(SubscriptionHandlerProvider stepProvider,
                                BotStateEvaluator stateEvaluator,
                                UpdateResultFactory updateResultFactory,
                                SubscriptionHandlerEvaluator handlerEvaluator) {
        super(StateName.SUBSCRIPTION);
        this.stepProvider = stepProvider;
        this.updateResultFactory = updateResultFactory;
        this.stateEvaluator = stateEvaluator;
        this.handlerEvaluator = handlerEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        final Optional<SubscriptionHandlerName> currentHandler =
            this.handlerEvaluator.getCurrentSubscriptionHandlerForUser(chatId);

        if (!currentHandler.isPresent()) {
            switch (text) {
                case "/maxPrice": return handleMaxPriceCommand(chatId);
                case "/minPrice": return handleMinPriceCommand(chatId);
                case "/city": return handleCityCommand(chatId);
                default: return this.updateResultFactory.createUpdateResult("replies.global.notSupported");
            }
        }

        return delegateToStep(chatId, update, currentHandler.get());
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final Optional<SubscriptionHandlerName> currentStepName =
            this.handlerEvaluator.getCurrentSubscriptionHandlerForUser(chatId);

        final Keyboard keyboard = new Keyboard();

        keyboard.addOneButton(new KeyboardButton("/cancel"));
        keyboard.addOneButton(new KeyboardButton("/save"));

        if (!currentStepName.isPresent()) {
            keyboard.addOneButton(new KeyboardButton("/minPrice"));
            keyboard.addOneButton(new KeyboardButton("/maxPrice"));
            keyboard.addOneButton(new KeyboardButton("/city"));
        } else {
            final SubscriptionHandler subscriptionHandler = this.stepProvider.getSubscriptionStep(currentStepName.get());
            keyboard.AddButtons(subscriptionHandler.getKeyboardButtons(update));
        }

        return keyboard;
    }

    private UpdateResult handleMinPriceCommand(long chatId) {
        this.handlerEvaluator.setSubscriptionStepForUser(chatId, SubscriptionHandlerName.MIN_PRICE);
        return this.updateResultFactory.createUpdateResult("replies.subscription.minPrice");
    }

    private UpdateResult handleMaxPriceCommand(long chatId) {
        this.handlerEvaluator.setSubscriptionStepForUser(chatId, SubscriptionHandlerName.MAX_PRICE);
        return this.updateResultFactory.createUpdateResult("replies.subscription.maxPrice");
    }

    private UpdateResult handleCityCommand(long chatId) {
        this.handlerEvaluator.setSubscriptionStepForUser(chatId, SubscriptionHandlerName.CITY);
        return this.updateResultFactory.createUpdateResult("replies.subscription.city");
    }

    private UpdateResult delegateToStep(long chatId, Update update, SubscriptionHandlerName handlerName) {
        final SubscriptionHandler step = this.stepProvider.getSubscriptionStep(handlerName);
        final UpdateResult updateResult = step.handleSubscriptionStep(update);
        //this.stepProvider.resetSubscriptionStepForUser(chatId);
        return updateResult;
    }
}
