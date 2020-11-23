package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main", description = "go to main menu"),
    @AcceptedCommand(commandName = "/my", description = "list my subscriptions"),
    @AcceptedCommand(commandName = "/unsubscribe", description = "delete a subscription")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/main" }),
    @KeyboardRow(buttons = { "/my" }),
    @KeyboardRow(buttons = { "/unsubscribe" }),
})
public class ManagementBotState extends AbstractBotState {
    private static final String HEADER = "Here you can manage your subscriptions.";

    private final SubscriptionService subscriptionService;

    @Autowired
    protected ManagementBotState(SubscriptionService subscriptionService) {
        super(BotStateName.MANAGEMENT);
        this.subscriptionService = subscriptionService;
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public CommandResult onUnsubscribe(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /unsubscribe command for user {}", command.getChatId());
        changeState.accept(BotStateName.UNSUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/main")
    public CommandResult onMain(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /main command for user {}", command.getChatId());
        changeState.accept(BotStateName.INITIAL);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command) {
        log.info("Executing /my command for user {}", command.getChatId());
        final Long chatId = command.getChatId();
        final String subscriptions = getSubscriptionsInfo(chatId);
        return CommandResult.withMessage(String.format("Your subscriptions:\n\n%s", subscriptions));
    }

    private String getSubscriptionsInfo(Long chatId) {
        return this.subscriptionService.getAllSubscriptionsForUser(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }
}
