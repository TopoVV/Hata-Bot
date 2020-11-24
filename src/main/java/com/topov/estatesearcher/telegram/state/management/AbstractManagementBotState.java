package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.StateProperties;
import com.topov.estatesearcher.telegram.state.subscription.CommandExecutor;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Collectors;

@Log4j2
public class AbstractManagementBotState extends AbstractBotState {
    protected final SubscriptionService subscriptionService;

    protected AbstractManagementBotState(StateProperties props, MessageSourceAdapter messageSource, SubscriptionService subscriptionService) {
        super(props, messageSource);
        this.subscriptionService = subscriptionService;
    }

    protected CommandResult defaultMy(TelegramCommand command, UserContext context) {
        return new MyCommandExecutor().executeCommand(command, context);
    }

    private final class MyCommandExecutor implements CommandExecutor {
        @Override
        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /my command for user {}", context.getChatId());
            final String chatId = context.getChatId();
            final String subscriptions = getSubscriptionsInfo(chatId);
            final String message = getMessage("management.my.reply", context, subscriptions);
            return CommandResult.withMessage(message);
        }
    }

    protected String getSubscriptionsInfo(String chatId) {
        return this.subscriptionService.getAllSubscriptionsForUser(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }

}
