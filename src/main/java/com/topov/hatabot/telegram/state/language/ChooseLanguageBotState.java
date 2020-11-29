package com.topov.hatabot.telegram.state.language;

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
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/en"),
    @AcceptedCommand(commandName = "/ru"),
    @AcceptedCommand(commandName = "/main"),
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/en", "/ru" }),
    @KeyboardRow(buttons = { "/main" })
})
public class ChooseLanguageBotState extends AbstractBotState {
    public ChooseLanguageBotState() { super(StateUtils.LANGUAGE_PROPS); }

    @CommandMapping(forCommand = "/en")
    public CommandResult onEn(TelegramCommand command, UserContext context) {
        log.info("Executing /ru command for user: {}", context.getUserId());
        context.setLocale(new Locale("en"));
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = MessageHelper.getMessage("reply.en", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/ru")
    public CommandResult onRu(TelegramCommand command, UserContext context) {
        log.info("Executing /ru command for user: {}", context.getUserId());
        context.setLocale(new Locale("ru"));
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = MessageHelper.getMessage("reply.ru", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getUserId());
        final DefaultMainExecutor executor = new DefaultMainExecutor();
        return executor.execute(command, context);
    }

}
