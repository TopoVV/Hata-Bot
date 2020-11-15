package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.provider.BotStateProvider;
import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.telegram.state.BotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Log4j2
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
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        final Optional<BotState.StateName> stateName = this.stateHolder.getUserCurrentStateName(chatId);
        if (!stateName.isPresent() || text.equals("/start")) {
            log.debug("Handling first interaction for user: {}", chatId);
            this.stateHolder.setStateForUser(chatId, BotState.StateName.INITIAL);
        }

        final BotState state = this.stateProvider.getBotState(stateName.orElse(BotState.StateName.INITIAL));
        return state.handleUpdate(update);
    }

    @Override
    public Hint getHint(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final Optional<BotState.StateName> stateName = this.stateHolder.getUserCurrentStateName(chatId);
        final BotState state = this.stateProvider.getBotState(stateName.orElse(BotState.StateName.INITIAL));

        return state.getHint(update);
    }

    @Override
    public Keyboard getKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final Optional<BotState.StateName> stateName = this.stateHolder.getUserCurrentStateName(chatId);
        final BotState state = this.stateProvider.getBotState(stateName.orElse(BotState.StateName.INITIAL));

        return state.createKeyboard(update);
    }
}
