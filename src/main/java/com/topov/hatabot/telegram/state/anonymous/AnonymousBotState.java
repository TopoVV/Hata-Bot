package com.topov.hatabot.telegram.state.anonymous;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.request.UpdateWrapper;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.EntranceMessage;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.AcceptedCommand;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/start")
})
public class AnonymousBotState extends AbstractBotState {
    public AnonymousBotState() {
        super(StateUtils.ANONYMOUS_PROPS);
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext context) {
        if (command.isStart()) {
            return this.getHandlers().get(command.getCommand()).act(command, context);
        }
        throw new RuntimeException("Anonymous interaction");
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        throw new RuntimeException("Anonymous interaction");
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        return Optional.empty();
    }

    @CommandMapping(forCommand = "/start")
    public CommandResult onStart(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
        final String message = MessageHelper.getMessage("reply.start", context);
        return CommandResult.withMessage(message);
    }
}
