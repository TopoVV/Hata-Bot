package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/unsubscribe")
})
public class ManagementBotState extends AbstractBotState {

    @Autowired
    protected ManagementBotState(MessageSource messageSource) {
        super(BotStateName.MANAGEMENT, messageSource);
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public CommandResult handleUnsubscribeCommand(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /unsubscribe command");
        changeState.accept(BotStateName.UNSUBSCRIBE);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("unsubscribe.entrance", null, Locale.ENGLISH));
    }
}
