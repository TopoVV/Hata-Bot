package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.context.UserContext;
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

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/start", description = "restart"),
    @AcceptedCommand(commandName = "/subscribe",description = "create a subscription"),
    @AcceptedCommand(commandName = "/subscriptions",description = "manage my subscriptions")
})
public class InitialBotState extends AbstractBotState {
    private static final String ENTRANCE_MESSAGE = "Main menu.";

    private final UserContextService contextService;

    @Autowired
    public InitialBotState(UserContextService contextService) {
        super(BotStateName.INITIAL);
        this.contextService = contextService;
    }

    @CommandMapping(forCommand = "/start")
    public CommandResult onStart(TelegramCommand command) {
        log.info("Executing /start command for user {}", command.getChatId());
        this.contextService.createContext(command.getChatId());
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult onSubscribe(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /subscribe command for user {}", command.getChatId());
        changeState.accept(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public CommandResult onSubscriptions(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /subscriptions command for user {}", command.getChatId());
        changeState.accept(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return String.format("%s\n\nCommands:\n%s", ENTRANCE_MESSAGE, commandsInformationString());
    }
}
