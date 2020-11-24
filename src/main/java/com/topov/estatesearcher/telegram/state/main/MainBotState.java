package com.topov.estatesearcher.telegram.state.main;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/subscriptions")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/subscribe" }),
    @KeyboardRow(buttons = { "/subscriptions" }),
})
public class MainBotState extends AbstractBotState {

    @Autowired
    public MainBotState(MessageSourceAdapter messageSource) {
        super(BotStateName.MAIN, "main.header", "main.commands", messageSource);
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult onSubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /subscribe command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public CommandResult onSubscriptions(TelegramCommand command, UserContext context) {
        log.info("Executing /subscriptions command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }
}
