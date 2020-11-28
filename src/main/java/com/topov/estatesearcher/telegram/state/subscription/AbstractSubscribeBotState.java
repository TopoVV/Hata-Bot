package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.context.SubscriptionConfig;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AbstractSubscribeBotState extends AbstractBotState {

    protected AbstractSubscribeBotState(StateProperties props) {
        super(props);
    }

    public static class DefaultCurrentExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context) {
            final SubscriptionConfig current = context.getSubscriptionConfig();
            final String message = MessageHelper.subscriptionConfigToMessage(current, context);
            return CommandResult.withMessage(message);
        }
    }
}
