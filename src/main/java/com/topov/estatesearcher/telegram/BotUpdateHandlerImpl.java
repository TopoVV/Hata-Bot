package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.Hint;
import com.topov.estatesearcher.telegram.reply.Keyboard;
import com.topov.estatesearcher.telegram.reply.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateProvider;
import com.topov.estatesearcher.telegram.state.UserBotStateEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotUpdateHandlerImpl implements BotUpdateHandler {
    private final UserBotStateEvaluator stateHolder;
    private final BotStateProvider stateProvider;

    @Autowired
    public BotUpdateHandlerImpl(UserBotStateEvaluator stateHolder, BotStateProvider stateProvider) {
        this.stateHolder = stateHolder;
        this.stateProvider = stateProvider;
    }


    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        if (text.equals("/start")) {
            return handleFirstUserInteraction(update);
        } else {
            final BotState.StateName stateForUser = this.stateHolder.getStateForUser(chatId);
            final BotState state = stateProvider.getBotState(stateForUser);
            return state.handleUpdate(update);
        }
    }

    @Override
    public Hint getHint(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final BotState.StateName stateForUser = this.stateHolder.getStateForUser(chatId);
        final BotState state = stateProvider.getBotState(stateForUser);

        return state.getHint(update);
    }

    @Override
    public Keyboard getKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final BotState.StateName stateForUser = this.stateHolder.getStateForUser(chatId);
        final BotState state = stateProvider.getBotState(stateForUser);
        return state.getKeyboard();
    }

    private UpdateResult handleFirstUserInteraction(Update update) {
        this.stateHolder.setStateForUser(update.getMessage().getChatId(), BotState.StateName.INITIAL);
        return new UpdateResult("Welcome");
    }
}
