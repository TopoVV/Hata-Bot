package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/minPrice"),
    @AcceptedCommand(commandName = "/maxPrice"),
    @AcceptedCommand(commandName = "/city"),
    @AcceptedCommand(commandName = "/cancel"),
    @AcceptedCommand(commandName = "/save"),
    @AcceptedCommand(commandName = "/main")
})
public class SubscriptionBotState extends AbstractSubscriptionBotState {
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    public SubscriptionBotState(BotStateEvaluator stateEvaluator, SubscriptionCache subscriptionCache, UpdateResultFactory updateResultFactory) {
        super(BotStateName.SUBSCRIPTION, stateEvaluator, subscriptionCache);
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        return new UpdateResult("SUBSCRIPTION BOT STATE");
    }

    @CommandMapping(forCommand = "/minPrice")
    public UpdateResult handleMinPriceCommand(Update update) {
        log.info("Executing /minPrice command");
        return new UpdateResult("/minPrice command executed");
    }

    @CommandMapping(forCommand = "/maxPrice")
    public UpdateResult handleMaxPriceCommand(Update update) {
        log.info("Executing /maxPrice command");
        return new UpdateResult("/maxPrice command executed");
    }

    @CommandMapping(forCommand = "/city")
    public UpdateResult handleCityCommand(Update update) {
        log.info("Executing /city command");
        this.stateEvaluator.setStateForUser(update.getMessage().getChatId(), BotStateName.SUBSCRIPTION_CITY);
        return new UpdateResult("/city command executed");
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
