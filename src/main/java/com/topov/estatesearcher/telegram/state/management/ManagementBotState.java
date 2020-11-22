package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/unsubscribe")
})
public class ManagementBotState extends AbstractBotState {

    @Autowired
    protected ManagementBotState() {
        super(BotStateName.MANAGEMENT);
    }

    @Override
    public String getEntranceMessage() {
        return "MANAGEMENT BOT STATE";
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public CommandResult handleUnsubscribeCommand(TelegramCommand command) {
        log.info("Executing /unsubscribe command");
        return new CommandResult(BotStateName.UNSUBSCRIBE, "/unsubscribe command executed");
    }
}
