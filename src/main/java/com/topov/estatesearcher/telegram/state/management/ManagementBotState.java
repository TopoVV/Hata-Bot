package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/unsubscribe")
})
public class ManagementBotState extends AbstractBotState {

    @Autowired
    protected ManagementBotState(BotStateEvaluator stateEvaluator) {
        super(BotStateName.MANAGEMENT, stateEvaluator);
    }

    @CommandMapping(forCommand = "/main")
    public UpdateResult handleMainCommand(Update update) {
        log.info("Executing /main command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.INITIAL);
        return new UpdateResult("/main command executed");
    }

    @CommandMapping(forCommand = "/unsubscribe")
    private UpdateResult handleUnsubscribeCommand(Update update) {
        log.info("Executing /unsubscribe command");
        final long chatId = update.getMessage().getChatId();
        this.stateEvaluator.setStateForUser(chatId, BotStateName.UNSUBSCRIBE);
        return new UpdateResult("/unsubscribe command executed");
    }

}
