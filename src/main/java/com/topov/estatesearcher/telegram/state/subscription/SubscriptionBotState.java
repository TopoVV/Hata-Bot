package com.topov.estatesearcher.telegram.state.subscription;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class SubscriptionBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    public SubscriptionBotState(BotStateEvaluator stateEvaluator,
                                UpdateResultFactory updateResultFactory) {
        super(BotStateName.SUBSCRIPTION);
        this.updateResultFactory = updateResultFactory;
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        return new UpdateResult("SUBSCRIPTION BOT STATE");
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        final long chatId = update.getMessage().getChatId();

        switch (command) {
            case "/minPrice": this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION_MIN_PRICE); break;
            case "/maxPrice": this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION_MAX_PRICE); break;
            case "/city": this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION_CITY); break;
            case "/cancel": this.stateEvaluator.setStateForUser(chatId, BotStateName.MANAGEMENT); break;
            case "/save": this.stateEvaluator.setStateForUser(chatId, BotStateName.MANAGEMENT); break;
        }

        return new UpdateResult("Command executed");
    }
//
//    @Override
//    public Keyboard createKeyboard(Update update) {
//        final Long chatId = update.getMessage().getChatId();
//        final Optional<SubscriptionHandlerName> currentStepName =
//            this.handlerEvaluator.getSubscriptionHandlerNameForUser(chatId);
//
//        final Keyboard keyboard = new Keyboard();
//
//        keyboard.addOneButton(new KeyboardButton("/cancel"));
//        keyboard.addOneButton(new KeyboardButton("/save"));
//
//        if (!currentStepName.isPresent()) {
//            keyboard.addOneButton(new KeyboardButton("/minPrice"));
//            keyboard.addOneButton(new KeyboardButton("/maxPrice"));
//            keyboard.addOneButton(new KeyboardButton("/city"));
//        } else {
//            final SubscriptionHandler subscriptionHandler = this.handlerProvider.getSubscriptionHandler(currentStepName.get());
//            keyboard.AddButtons(subscriptionHandler.getKeyboardButtons(update));
//        }
//
//        return keyboard;
//    }
//
//    private UpdateResult delegateToStep(long chatId, Update update, SubscriptionHandlerName handlerName) {
//        final SubscriptionHandler step = this.handlerProvider.getSubscriptionHandler(handlerName);
//        final UpdateResult updateResult = step.handleAction(update);
//        //this.stepProvider.resetSubscriptionStepForUser(chatId);
//        return updateResult;
//    }
}
