package com.topov.estatesearcher.telegram.state.anonymous;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/start",description = "start.description")
})
public class AnonymousBotState extends AbstractBotState {

    public AnonymousBotState() {
        super(BotStateName.ANONYMOUS);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        throw new RuntimeException("Anonymous interaction");
    }

    @CommandMapping(forCommand = "/start")
    public CommandResult onStart(TelegramCommand command, UserContext context) {
        context.changeState(BotStateName.MAIN);
        return CommandResult.withMessage("Welcome");
    }
}
