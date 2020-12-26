package com.topov.hatabot.telegram.state.subscription;

import com.topov.hatabot.SingleItemContent;
import com.topov.hatabot.telegram.context.SubscriptionConfig;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.AbstractBotState;
import com.topov.hatabot.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AbstractSubscribeBotState extends AbstractBotState {

    protected AbstractSubscribeBotState(StateProperties props) {
        super(props);
    }

    public static class DefaultCurrentExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context) {
            final SubscriptionConfig current = context.getSubscriptionConfig();
            return new CommandResult("reply.current", new SingleItemContent<>(current));
        }
    }
}
