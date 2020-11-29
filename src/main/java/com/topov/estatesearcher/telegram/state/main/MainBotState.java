package com.topov.estatesearcher.telegram.state.main;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.utils.StateUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/management"),
    @AcceptedCommand(commandName = "/language"),
    @AcceptedCommand(commandName = "/donate")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/subscribe", "/management" }),
    @KeyboardRow(buttons = { "/donate", "/language" })
})
public class MainBotState extends AbstractBotState {
    public MainBotState() {
        super(StateUtils.MAIN_PROPS);
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult onSubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /subscribe command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/management" )
    public CommandResult onSubscriptions(TelegramCommand command, UserContext context) {
        log.info("Executing /management command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/language" )
    public CommandResult onLanguage(TelegramCommand command, UserContext context) {
        log.info("Executing /language command for user {}", context.getUserId());
        final DefaultLanguageExecutor executor = new DefaultLanguageExecutor();
        return executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        log.info("Executing /donate command for user: {}", context.getUserId());
        final DefaultDonateExecutor executor = new DefaultDonateExecutor();
        return executor.execute(command, context);
    }
}
