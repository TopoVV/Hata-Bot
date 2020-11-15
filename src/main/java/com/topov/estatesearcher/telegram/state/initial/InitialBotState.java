package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.UserBotStateEvaluator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

@Log4j2
@Service
public class InitialBotState extends AbstractBotState {
    private final UserBotStateEvaluator stateEvaluator;

    @Autowired
    public InitialBotState(UserBotStateEvaluator stateEvaluator) {
        super(StateName.INITIAL);
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        if (text.equals("/subscribe")) {
            stateEvaluator.setStateForUser(chatId, StateName.SUBSCRIPTION);
            return new UpdateResult("Let's subscribe you");
        } else {
            return new UpdateResult("The command not supported");
        }
    }

    @Override
    public Hint getHint(Update update) {
        return new Hint("/subscribe - create a subscription");
    }

    @Override
    public Keyboard getKeyboard() {
        final KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(new KeyboardButton("/subscribe"));
        return new Keyboard(Collections.singletonList(keyboardButtons));
    }
}
