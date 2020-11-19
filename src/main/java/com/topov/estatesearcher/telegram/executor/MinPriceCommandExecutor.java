package com.topov.estatesearcher.telegram.executor;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.Set;

@Log4j2
@Service
public class MinPriceCommandExecutor implements CommandExecutor {
    private final BotStateEvaluator stateEvaluator;

    @Autowired
    public MinPriceCommandExecutor(BotStateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public void execute(Update update) {
        log.info("Executing /minPrice command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION_MIN_PRICE);
    }

    @Override
    public Set<String> executes() {
        return Collections.singleton("/minPrice");
    }
}
