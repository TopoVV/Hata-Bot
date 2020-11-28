package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.service.SubscriptionList;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.StateProperties;
import com.topov.estatesearcher.telegram.state.subscription.MessageHelper;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public class AbstractManagementBotState extends AbstractBotState {
    protected final SubscriptionService subscriptionService;

    protected AbstractManagementBotState(StateProperties props, SubscriptionService subscriptionService) {
        super(props);
        this.subscriptionService = subscriptionService;
    }

    public static class DefaultMyExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context, SubscriptionList subscriptionList) {
            final String subscriptions = subscriptionList.toString(context);
            final String message = MessageHelper.getMessage("reply.my", context, subscriptions);
            return CommandResult.withMessage(message);
        }
    }
}
