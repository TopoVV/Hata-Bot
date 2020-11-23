package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.response.BotResponse;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Log4j2
@Service
public class BotUpdateProcessorImpl implements BotUpdateProcessor {
    private final UserContextService contextService;
    private final Map<BotStateName, BotState> states;

    @Autowired
    public BotUpdateProcessorImpl(UserContextService contextService, List<AbstractBotState> states) {
        this.contextService = contextService;
        final Map<BotStateName, BotState> map = states.stream()
            .collect(toMap(
                AbstractBotState::getBotStateName,
                Function.identity()
            ));
        this.states = new HashMap<>(map);

    }

    @Override
    public Optional<BotResponse> processUpdate(UpdateWrapper update) {
        final Long chatId = update.getChatId();
        final UserContext context = this.contextService.getContextForUser(chatId);
        final BotState currentState = this.states.get(context.getCurrentStateName());

        if (update.isCommand()) {
            final CommandResult commandResult = context.executeCommand(update.unwrapCommand(), currentState);
            this.contextService.setContext(context);
            return commandResult.createResponse(chatId);
        } else {
            final UpdateResult updateResult = context.handleUpdate(update.unwrapUpdate(), currentState);
            this.contextService.setContext(context);
            return updateResult.createResponse(chatId);
        }
    }

    public SendMessage getMessage(UpdateWrapper update) {
        final UserContext context = this.contextService.getContextForUser(update.getChatId());
        final BotState currentState = this.states.get(context.getCurrentStateName());

        final String entranceMessage = context.getEntranceMessage(currentState, update);
        final Keyboard keyboard = currentState.getKeyboard();
        final SendMessage message = new SendMessage(update.getChatId().toString(), entranceMessage);
        message.setReplyMarkup(keyboard.createKeyboardMarkup());
        return message;
    }


    @Override
    public Keyboard getKeyboard(Update update) {
        return new Keyboard();
    }
}
