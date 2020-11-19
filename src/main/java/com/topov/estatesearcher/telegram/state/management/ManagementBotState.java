package com.topov.estatesearcher.telegram.state.management;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.provider.CommandExecutorProvider;
import com.topov.estatesearcher.telegram.state.BotStateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ManagementBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;
    private final CommandExecutorProvider executorProvider;

    @Autowired
    protected ManagementBotState(BotStateEvaluator stateEvaluator, CommandExecutorProvider executorProvider) {
        super(BotStateName.MANAGEMENT);
        this.stateEvaluator = stateEvaluator;
        this.executorProvider = executorProvider;
        this.supportedCommands = Lists.newArrayList("/unsubscribe");
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();

        if (isSupportedCommand(text)) {
            this.executorProvider.getExecutor(text).ifPresent(executor -> executor.execute(update));
        }

        return new UpdateResult("MANAGEMENT BOT STATE");
    }
}
