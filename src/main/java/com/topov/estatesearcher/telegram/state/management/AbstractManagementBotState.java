package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public class AbstractManagementBotState extends AbstractBotState {
    protected final SubscriptionService subscriptionService;

    protected AbstractManagementBotState(StateProperties props, MessageSourceAdapter messageSource, SubscriptionService subscriptionService) {
        super(props, messageSource);
        this.subscriptionService = subscriptionService;
    }

    public static class DefaultMyExecutor implements DefaultExecutor {
        private final MessageSourceAdapter messageSource;
        private final SubscriptionService subscriptionService;

        DefaultMyExecutor(MessageSourceAdapter messageSource, SubscriptionService subscriptionService) {
            this.messageSource = messageSource;
            this.subscriptionService = subscriptionService;
        }

        @Override
        public CommandResult execute(TelegramCommand command, UserContext context) {
            final String chatId = context.getChatId();
            final String subscriptions = this.subscriptionService.getUserSubscriptionsInfo(chatId);
            final String message = this.messageSource.getMessage("management.my.reply", context, subscriptions);
            return CommandResult.withMessage(message);
        }
    }
}
