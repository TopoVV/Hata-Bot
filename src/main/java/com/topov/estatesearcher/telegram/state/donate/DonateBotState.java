package com.topov.estatesearcher.telegram.state.donate;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.StateUtils;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/later")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/later" })
})
public class DonateBotState extends AbstractBotState {

    @Autowired
    public DonateBotState(MessageSourceAdapter messageSource) {
        super(StateUtils.DONATE_PROPS, messageSource);
    }

    @CommandMapping(forCommand = "/later")
    public CommandResult onLater(TelegramCommand command, UserContext context) {
        log.info("Executing /later command for user: {}", context.getChatId());
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = getMessage("donate.later.reply", context);
        return CommandResult.withMessage(message);
    }
}
