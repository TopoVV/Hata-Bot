package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
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

import java.util.stream.Collectors;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/my"),
    @AcceptedCommand(commandName = "/unsubscribe"),
    @AcceptedCommand(commandName = "/language"),
    @AcceptedCommand(commandName = "/donate")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/main", "/my" }),
    @KeyboardRow(buttons = { "/unsubscribe" }),
    @KeyboardRow(buttons = { "/donate", "/language" }),
})
public class ManagementBotState extends AbstractManagementBotState {

    @Autowired
    public ManagementBotState(SubscriptionService subscriptionService, MessageSourceAdapter messageSource) {
        super(StateUtils.MANAGEMENT_PROPS, messageSource, subscriptionService);
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public CommandResult onUnsubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /unsubscribe command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.UNSUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        return this.defaultMain(command, context);
    }

    @CommandMapping(forCommand = "/language" )
    public CommandResult onLanguage(TelegramCommand command, UserContext context) {
        return this.defaultLanguage(command, context);
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        return this.defaultMy(command, context);
    }

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        return this.defaultDonate(command, context);
    }
}
