package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Log4j2
@Service
public class BotUpdateProcessorImpl implements BotUpdateProcessor {
    private final BotStateEvaluator stateEvaluator;
    private final UpdateResultFactory updateResultFactory;
    private final Map<BotStateName, AbstractBotState> states;

    private final UserContextService contextService;

    @Autowired
    public BotUpdateProcessorImpl(BotStateEvaluator stateEvaluator,
                                  UpdateResultFactory updateResultFactory,
                                  List<AbstractBotState> states, UserContextService contextService) {
        this.stateEvaluator = stateEvaluator;
        this.updateResultFactory = updateResultFactory;
        this.states = states.stream()
            .collect(toMap(
                AbstractBotState::getBotStateName,
                Function.identity()
                )
            );
        this.contextService = contextService;
    }

    @Override
    public UpdateResult processUpdate(UpdateWrapper update) {
        final Long chatId = update.getChatId();

        final UserContext context = this.contextService.getContextForUser(chatId);
//        final BotStateName stateName = this.stateEvaluator.getUserCurrentStateName(chatId);
//        final BotState state = this.states.get(stateName);
        final BotState state = this.states.get(context.getStateName());
        if (update.isCommand()) {
            return state.executeCommand(text, update);
        } else {
            return state.handleUpdate(update);
        }
    }

    @Override
    public UpdateResult processFirstInteraction(long chatId) {
        log.debug("Handling first interaction for user: {}", chatId);
        this.stateEvaluator.setStateForUser(chatId, BotStateName.INITIAL);
        return this.updateResultFactory.createUpdateResult("replies.start", "commands.initial");
    }

    @Override
    public Keyboard getKeyboard(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final BotStateName stateName = this.stateEvaluator.getUserCurrentStateName(chatId);
        final BotState state = states.get(stateName);
        return state.createKeyboard(update);
    }

}
