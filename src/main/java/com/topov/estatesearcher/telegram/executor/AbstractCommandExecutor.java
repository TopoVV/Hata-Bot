package com.topov.estatesearcher.telegram.executor;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.executor.CommandExecutor;

public abstract class AbstractCommandExecutor implements CommandExecutor {
    protected BotStateEvaluator stateEvaluator;
}
