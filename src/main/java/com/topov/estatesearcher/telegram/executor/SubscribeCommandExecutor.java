package com.topov.estatesearcher.telegram.executor;


import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.state.BotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Log4j2
@Component
public class SubscribeCommandExecutor extends AbstractCommandExecutor {

    @Autowired
    public SubscribeCommandExecutor(BotStateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public void execute(Update update) {
        log.info("Executing /subscribe command!!!");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotState.StateName.SUBSCRIPTION);
    }

    @Override
    public Set<String> executes(){
        return Collections.singleton("/subscribe");
    }
}
