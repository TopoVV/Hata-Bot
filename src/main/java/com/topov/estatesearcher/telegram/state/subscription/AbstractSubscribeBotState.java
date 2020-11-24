package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AbstractSubscribeBotState extends AbstractBotState {
    protected final SubscriptionCache subscriptionCache;

    protected AbstractSubscribeBotState(StateProperties props, MessageSourceAdapter messageSource, SubscriptionCache subscriptionCache) {
        super(props, messageSource);
        this.subscriptionCache = subscriptionCache;
    }

    protected CommandResult defaultCurrent(TelegramCommand command, UserContext context) {
        return new CurrentCommandExecutor().executeCommand(command, context);
    }

    protected CommandResult defaultBack(TelegramCommand command, UserContext context) {
        return new BackCommandExecutor().executeCommand(command, context);
    }

    private final class CurrentCommandExecutor implements CommandExecutor {

        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /cancel command for user {}", context.getChatId());

            final String current = subscriptionCache.getCachedSubscription(context.getChatId())
                .map(Subscription::toString)
                .orElse(getMessage("subscribe.current.notCreated.reply", context));

            final String message = getMessage("subscribe.current.success.reply", context, current);
            return CommandResult.withMessage(message);
        }
    }

    private final class BackCommandExecutor implements CommandExecutor {
        @Override
        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /back command for user {}", context.getChatId());
            context.setCurrentStateName(BotStateName.SUBSCRIBE);
            return CommandResult.empty();
        }
    }
}
