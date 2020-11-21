package com.topov.estatesearcher.telegram.state.initial;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Map;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/subscriptions")
})
public class InitialBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;

    @Autowired
    public InitialBotState(BotStateEvaluator stateEvaluator) {
        super(BotStateName.INITIAL);
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        return this.actions.get(command).act(update);
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

    @CommandHandler(forCommand = "/subscribe" )
    public UpdateResult handleSubscribe(Update update) {
        log.info("Executing /subscribe command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION);
        return new UpdateResult("/subscribe command executed");
    }

    @CommandHandler(forCommand = "/subscriptions" )
    public UpdateResult commandHandler(Update update) {
        log.info("Executing /subscriptions command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.MANAGEMENT);
        return new UpdateResult("/subscriptions command executed");
    }
}
