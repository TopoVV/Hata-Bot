package com.topov.hatabot.telegram.state.management;

import com.topov.hatabot.model.SubscriptionList;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.StateProperties;
import com.topov.hatabot.utils.MessageHelper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AbstractManagementBotState extends AbstractBotState {
    protected final SubscriptionService subscriptionService;

    protected AbstractManagementBotState(StateProperties props, SubscriptionService subscriptionService) {
        super(props);
        this.subscriptionService = subscriptionService;
    }

    public static class DefaultMyExecutor {
        public String execute(TelegramCommand command, UserContext context, SubscriptionList subscriptionList) {
            final String subscriptions = subscriptionList.toString(context);
            return MessageHelper.getMessage("reply.my", context, subscriptions);
        }
    }
}
