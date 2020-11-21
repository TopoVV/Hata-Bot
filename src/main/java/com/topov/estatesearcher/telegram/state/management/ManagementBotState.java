package com.topov.estatesearcher.telegram.state.management;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.initial.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.initial.CommandHandler;
import com.topov.estatesearcher.telegram.state.initial.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/unsubscribe")
})
public class ManagementBotState extends AbstractBotState {
    private final BotStateEvaluator stateEvaluator;

    @Autowired
    protected ManagementBotState(BotStateEvaluator stateEvaluator) {
        super(BotStateName.MANAGEMENT);
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        return this.actions.get(command).act(update);
    }

    @CommandHandler(forCommand = "/main")
    public UpdateResult handleMainCommand(Update update) {
        log.info("Executing /main command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.INITIAL);
        return new UpdateResult("/main command executed");
    }

    @CommandHandler(forCommand = "/unsubscribe")
    private UpdateResult handleUnsubscribeCommand(Update update) {
        log.info("Executing /unsubscribe command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.UNSUBSCRIBE);
        return new UpdateResult("/unsubscribe command executed");
    }

}
