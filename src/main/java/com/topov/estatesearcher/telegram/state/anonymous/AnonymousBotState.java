package com.topov.estatesearcher.telegram.state.anonymous;

import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/start")
})
public class AnonymousBotState extends AbstractBotState {

    @Autowired
    public AnonymousBotState(MessageSourceAdapter messageSource) {
        super(BotStateName.ANONYMOUS, "", "", messageSource);
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
        context.setCurrentStateName(BotStateName.MAIN);
        return CommandResult.withMessage("Welcome");
    }
}
