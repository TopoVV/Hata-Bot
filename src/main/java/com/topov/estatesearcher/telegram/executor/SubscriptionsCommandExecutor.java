package com.topov.estatesearcher.telegram.executor;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Log4j2
@Component
public class SubscriptionsCommandExecutor extends AbstractCommandExecutor {

    @Autowired
    public SubscriptionsCommandExecutor(BotStateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public void execute(Update update) {
        log.info("Executing /subscriptions command!!!");
    }

    @Override
    public Set<String> executes() {
        return Collections.singleton("/subscriptions");
    }
}
