package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.service.BotStateEvaluator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.ws.rs.HEAD;
import java.util.Collections;

@Log4j2
@Service
public class InitialBotState extends AbstractBotState {
    @Autowired
    public InitialBotState(BotStateEvaluator stateEvaluator) {
        super(StateName.INITIAL, stateEvaluator);
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final java.lang.String text = update.getMessage().getText();

        if (text.equals("/start")) {
            return new UpdateResult("Welcome");
        } else if (text.equals("/subscribe")) {
            this.stateEvaluator.setStateForUser(chatId, StateName.SUBSCRIPTION);
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
    public Keyboard createKeyboard(Update update) {
        final KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(new KeyboardButton("/subscribe"));
        return new Keyboard(Collections.singletonList(keyboardButtons));
    }
}
