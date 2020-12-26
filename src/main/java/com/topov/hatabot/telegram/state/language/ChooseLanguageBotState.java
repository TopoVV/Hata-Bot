package com.topov.hatabot.telegram.state.language;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;

@Log4j2
@TelegramBotState
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/en", "/ru" }),
    @KeyboardRow(buttons = { "/main" })
})
public class ChooseLanguageBotState extends AbstractBotState {
    public ChooseLanguageBotState() { super(StateUtils.LANGUAGE_PROPS); }

    @CommandMapping(forCommand = "/en")
    public CommandResult onEn(TelegramCommand command, UserContext context) {
        context.setLocale(new Locale("en"));
        context.setCurrentStateName(BotStateName.MAIN);
        return new CommandResult("reply.en");
    }

    @CommandMapping(forCommand = "/ru")
    public CommandResult onRu(TelegramCommand command, UserContext context) {
        context.setLocale(new Locale("ru"));
        context.setCurrentStateName(BotStateName.MAIN);
        return new CommandResult("reply.ru");
    }

    @CommandMapping(forCommand = "/main")
    public void onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getUserId());
        final DefaultMainExecutor executor = new DefaultMainExecutor();
        executor.execute(command, context);
    }

}
