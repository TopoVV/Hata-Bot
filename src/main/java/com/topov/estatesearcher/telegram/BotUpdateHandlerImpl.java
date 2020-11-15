package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.provider.BotStateProvider;
import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.telegram.state.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotUpdateHandlerImpl implements BotUpdateHandler {
    private final BotStateEvaluator stateHolder;
    private final BotStateProvider stateProvider;

    @Autowired
    public BotUpdateHandlerImpl(BotStateEvaluator stateHolder, BotStateProvider stateProvider) {
        this.stateHolder = stateHolder;
        this.stateProvider = stateProvider;
    }


    @Override
    public UpdateResult handleUpdate(Update update) {
        final java.lang.String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        if (text.equals("/start")) {
            this.stateHolder.setStateForUser(chatId, BotState.StateName.INITIAL);
        }

        final BotState.StateName stateForUser = this.stateHolder.getStateForUser(chatId);
        final BotState state = stateProvider.getBotState(stateForUser);
        return state.handleUpdate(update);
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

        return state.createKeyboard(update);
    }
}
