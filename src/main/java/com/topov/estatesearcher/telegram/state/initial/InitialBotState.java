package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/subscriptions")
})
public class InitialBotState extends AbstractBotState {

    @Autowired
    public InitialBotState(BotStateEvaluator stateEvaluator) {
        super(BotStateName.INITIAL, stateEvaluator);
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

    @CommandMapping(forCommand = "/subscribe" )
    public UpdateResult handleSubscribe(Update update) {
        log.info("Executing /subscribe command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION);
        return new UpdateResult("/subscribe command executed");
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public UpdateResult commandHandler(Update update) {
        log.info("Executing /subscriptions command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.MANAGEMENT);
        return new UpdateResult("/subscriptions command executed");
    }
}
