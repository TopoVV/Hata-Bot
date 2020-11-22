package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.state.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            return Optional.of(commandResult.createResponse());
        } else {
            final UpdateResult updateResult = context.handleUpdate(update.unwrapUpdate(), currentState);
            this.contextService.setContext(context);
            return Optional.of(updateResult.createResponse());
        }
    }

    @Override
    public Keyboard getKeyboard(Update update) {
        return new Keyboard();
    }
}
