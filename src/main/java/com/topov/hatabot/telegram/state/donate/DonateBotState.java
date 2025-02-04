package com.topov.hatabot.telegram.state.donate;

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

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/later")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/later" })
})
public class DonateBotState extends AbstractBotState {
    public DonateBotState() {
        super(StateUtils.DONATE_PROPS);
    }

    @CommandMapping(forCommand = "/later")
    public CommandResult onLater(TelegramCommand command, UserContext context) {
        log.info("Executing /later command for user: {}", context.getUserId());
        context.setCurrentStateName(BotStateName.MAIN);
        final String message = MessageHelper.getMessage("reply.donate.later", context);
        return CommandResult.withMessage(message);
    }
}
