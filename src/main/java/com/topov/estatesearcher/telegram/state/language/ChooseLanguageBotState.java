package com.topov.estatesearcher.telegram.state.language;

import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.StateUtils;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.Optional;

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

    @Autowired
    protected ChooseLanguageBotState(MessageSourceAdapter messageSource) {
        super(StateUtils.LANGUAGE_PROPS, messageSource);
    }

    @CommandMapping(forCommand = "/en")
    public CommandResult onEn(TelegramCommand command, UserContext context) {
        log.info("Executing /ru command for user: {}", context.getChatId());
        context.setLocale(new Locale("en"));
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = getMessage("language.en.reply", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/ru")
    public CommandResult onRu(TelegramCommand command, UserContext context) {
        log.info("Executing /ru command for user: {}", context.getChatId());
        context.setLocale(new Locale("ru"));
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = getMessage("language.ru.reply", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        return this.defaultMain(command, context);
    }


}
