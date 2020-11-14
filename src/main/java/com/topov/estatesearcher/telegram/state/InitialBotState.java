package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.BotStateHolder;
import com.topov.estatesearcher.telegram.Hint;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.Optional;

@Service
@Log4j2
@Scope(value = "prototype")
public class InitialBotState implements BotState {

    private final BotStateHolder stateHolder;

    @Autowired
    public InitialBotState(BotStateHolder stateHolder) {
        this.stateHolder = stateHolder;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();

        if (text.equals("/subscribe")) {
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
