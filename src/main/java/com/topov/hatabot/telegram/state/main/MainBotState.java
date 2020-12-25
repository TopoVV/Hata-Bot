package com.topov.hatabot.telegram.state.main;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.AcceptedCommand;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.StateUtils;
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
    public void onSubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /subscribe command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
    }

    @CommandMapping(forCommand = "/management" )
    public void onSubscriptions(TelegramCommand command, UserContext context) {
        log.info("Executing /management command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
    }

    @CommandMapping(forCommand = "/language" )
    public void onLanguage(TelegramCommand command, UserContext context) {
        log.info("Executing /language command for user {}", context.getUserId());
        final DefaultLanguageExecutor executor = new DefaultLanguageExecutor();
        executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/donate")
    public String onDonate(TelegramCommand command, UserContext context) {
        log.info("Executing /donate command for user: {}", context.getUserId());
        final DefaultDonateExecutor executor = new DefaultDonateExecutor();
        return executor.execute(command, context);
    }
}
