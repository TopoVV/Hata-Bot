package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        this.states = states.stream()
            .collect(toMap(
                AbstractBotState::getBotStateName,
                Function.identity()
                )
            );
    }

    @Override
    public Optional<BotResponse> processUpdate(UpdateWrapper update) {
        if (update.isCommand()) {
            return processCommand(update.unwrapCommand());
        } else {
            return Optional.empty();
        }

    }


    @Override
    public Keyboard getKeyboard(Update update) {
        return new Keyboard();
    }

    public Optional<BotResponse> processCommand(TelegramCommand command) {
        final Long chatId = command.getChatId();
        final UserContext context = this.contextService.getContextForUser(chatId);
        final CommandResult commandResult = context.executeCommand(command, this.states);
        this.contextService.setContext(new UserContext(context));

        final BotResponse response = BotResponse.builder()
            .forUser(chatId)
            .stateMessage(context.getCurrentStateEntranceMessage(this.states))
            .reply(commandResult.getMessage())
            .build();

        return Optional.of(response);
    }

}
