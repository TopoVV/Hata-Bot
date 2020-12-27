package com.topov.hatabot.telegram.state.management;

import com.topov.hatabot.ListItemContent;
import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class AbstractManagementBotState extends AbstractBotState {
    protected final SubscriptionService subscriptionService;

    protected AbstractManagementBotState(StateProperties props, SubscriptionService subscriptionService) {
        super(props);
        this.subscriptionService = subscriptionService;
    }

    protected CommandResult onMy(TelegramCommand command, UserContext context, List<Subscription> subscriptions) {
        return new CommandResult("reply.my", new ListItemContent<>(subscriptions));
    }
}
