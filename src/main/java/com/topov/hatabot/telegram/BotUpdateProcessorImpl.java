package com.topov.hatabot.telegram;

import com.topov.hatabot.message.converter.ContentConverter;
import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.service.UserContextService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.UpdateWrapper;
import com.topov.hatabot.telegram.response.BotResponse;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.EntranceMessage;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.BotState;
import com.topov.hatabot.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Log4j2
@Service
public class BotUpdateProcessorImpl implements BotUpdateProcessor {
    private final UserContextService contextService;
    private final MessageSourceAdapter messageSourceAdapter;
    private final ContentConverter converter;
    private final Map<BotStateName, BotState> states;

    @Autowired
    public BotUpdateProcessorImpl(UserContextService contextService,
                                  MessageSourceAdapter messageSourceAdapter,
                                  ContentConverter converter, List<AbstractBotState> states) {
        this.contextService = contextService;
        this.messageSourceAdapter = messageSourceAdapter;
        this.converter = converter;
        this.states = states.stream().collect(toMap(AbstractBotState::getStateName, Function.identity()));
    }

    @Override
    public Optional<BotResponse> processUpdate(UpdateWrapper update) {
        final String chatId = update.getChatId();
        final UserContext context = this.contextService.getContextForUser(update.getUserId());
        final BotState currentState = this.states.get(context.getCurrentStateName());

        if (update.isCommand()) {
            final CommandResult commandResult = context.executeCommand(update.unwrapCommand(), currentState);
            this.contextService.setContext(context);
            return commandResult.stringify(messageSourceAdapter, converter, context)
                .map(reply -> new BotResponse(chatId, reply));
        } else {
            final UpdateResult updateResult = context.handleUpdate(update.unwrapUpdate(), currentState);
            this.contextService.setContext(context);
            return Optional.of(new BotResponse(chatId, updateResult.stringify(messageSourceAdapter, context)));
        }
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update) {
        final UserContext context = this.contextService.getContextForUser(update.getChatId());
        final BotState currentState = this.states.get(context.getCurrentStateName());
        return context.getEntranceMessage(currentState, update);
    }
}
