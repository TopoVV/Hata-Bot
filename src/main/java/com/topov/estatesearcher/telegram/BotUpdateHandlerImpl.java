package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.InitialBotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class BotUpdateHandlerImpl implements BotUpdateHandler {
    private final BotStateHolder stateHolder;

    @Autowired
    public BotUpdateHandlerImpl(BotStateHolder stateHolder) {
        this.stateHolder = stateHolder;
    }


    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        if (text.equals("/start")) {
            return handleFirstUserInteraction(update);
        } else {
            final BotState state = this.stateHolder.getStateForUser(chatId);
            return state.handleUpdate(update);
        }
    }

    @Override
    public Hint getHint(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final BotState state = this.stateHolder.getStateForUser(chatId);

        return state.getHint(update);
    }

    @Override
    public Keyboard getKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final BotState state = this.stateHolder.getStateForUser(chatId);
        return state.getKeyboard();
    }

    private UpdateResult handleFirstUserInteraction(Update update) {
        this.stateHolder.setStateForUser(update.getMessage().getChatId(), createInitialBotState());
        return new UpdateResult("Welcome");
    }

    @Lookup
    public InitialBotState createInitialBotState() { return null; }
}
