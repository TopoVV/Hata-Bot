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
    @AcceptedCommand(commandName = "/unsubscribe")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/main" }),
    @KeyboardRow(buttons = { "/my" }),
    @KeyboardRow(buttons = { "/unsubscribe" }),
})
public class ManagementBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public ManagementBotState(SubscriptionService subscriptionService, MessageSourceAdapter messageSource) {
        super(BotStateName.MANAGEMENT, "management.header", "management.commands", messageSource);
        this.subscriptionService = subscriptionService;
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public CommandResult onUnsubscribe(TelegramCommand command, UserContext context) {
        log.info("Executing /unsubscribe command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.UNSUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.MAIN);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        log.info("Executing /my command for user {}", context.getChatId());
        final String chatId = context.getChatId();
        final String subscriptions = getSubscriptionsInfo(chatId);
        return CommandResult.withMessage(String.format("Your subscriptions:\n\n%s", subscriptions));
    }

    private String getSubscriptionsInfo(String chatId) {
        return this.subscriptionService.getAllSubscriptionsForUser(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }
}
