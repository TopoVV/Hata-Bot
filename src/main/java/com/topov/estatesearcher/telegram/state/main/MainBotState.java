package com.topov.estatesearcher.telegram.state.main;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.StateUtils;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/management"),
    @AcceptedCommand(commandName = "/language"),
    @AcceptedCommand(commandName = "/donate")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/subscribe", "/management" }),
    @KeyboardRow(buttons = { "/language" }),
    @KeyboardRow(buttons = { "/donate" })
})
public class MainBotState extends AbstractBotState {

    @Autowired
    public MainBotState(MessageSourceAdapter messageSource) {
        super(StateUtils.MAIN_PROPS, messageSource);
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult onSubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /subscribe command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/management" )
    public CommandResult onSubscriptions(TelegramCommand command, UserContext context) {
        log.info("Executing /management command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/language" )
    public CommandResult onLanguage(TelegramCommand command, UserContext context) {
        log.info("Executing /language command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        log.info("Executing /donate command for user: {}", context.getChatId());
        context.setCurrentStateName(BotStateName.DONATE);
        final String message = getMessage("main.donate.reply", context);
        return CommandResult.withMessage(message);
    }
}
