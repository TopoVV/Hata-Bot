package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel"),
    @AcceptedCommand(commandName = "/save"),
    @AcceptedCommand(commandName = "/minPrice"),
    @AcceptedCommand(commandName = "/maxPrice"),
    @AcceptedCommand(commandName = "/city"),
})
public class SubscriptionBotState extends AbstractSubscriptionBotState {

    @Autowired
    public SubscriptionBotState(SubscriptionCache subscriptionCache) {
        super(BotStateName.SUBSCRIPTION, subscriptionCache);
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        return new UpdateResult("SUBSCRIPTION BOT STATE");
    }

    @Override
    public String getEntranceMessage() {
        return "SUBSCRIPTION BOT STATE";
    }

    @CommandMapping(forCommand = "/minPrice")
    public CommandResult handleMinPriceCommand(TelegramCommand command) {
        log.info("Executing /minPrice command");
        return new CommandResult(BotStateName.SUBSCRIPTION_MIN_PRICE, "/minPrice command executed");
    }

    @CommandMapping(forCommand = "/maxPrice")
    public CommandResult handleMaxPriceCommand(TelegramCommand command) {
        log.info("Executing /maxPrice command");
        return new CommandResult(BotStateName.SUBSCRIPTION_MAX_PRICE, "/maxPrice command executed");
    }

    @CommandMapping(forCommand = "/city")
    public CommandResult handleCityCommand(TelegramCommand command) {
        log.info("Executing /city command");
        return new CommandResult(BotStateName.SUBSCRIPTION_CITY, "/city command executed");
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
