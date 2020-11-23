package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe",description = "subscribe.description"),
    @AcceptedCommand(commandName = "/subscriptions",description = "subscriptions.description")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/subscribe" }),
    @KeyboardRow(buttons = { "/subscriptions" }),
})
public class InitialBotState extends AbstractBotState {
    private static final String HEADER = "Main menu.";


    @Autowired
    public InitialBotState() {
        super(BotStateName.MAIN);
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult onSubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /subscribe command for user {}", command.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public CommandResult onSubscriptions(TelegramCommand command, UserContext context) {
        log.info("Executing /subscriptions command for user {}", command.getChatId());
        context.changeState(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }
}
