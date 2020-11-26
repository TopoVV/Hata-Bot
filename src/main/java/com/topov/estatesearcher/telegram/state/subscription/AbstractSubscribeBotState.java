package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.StateProperties;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public class AbstractSubscribeBotState extends AbstractBotState {

    protected AbstractSubscribeBotState(StateProperties props, MessageSourceAdapter messageSource) {
        super(props, messageSource);
    }

    public static class DefaultCurrentExecutor implements DefaultExecutor {
        private final MessageSourceAdapter messageSource;

        DefaultCurrentExecutor(MessageSourceAdapter messageSource) {
            this.messageSource = messageSource;
        }

        @Override
        public CommandResult execute(TelegramCommand command, UserContext context) {
            final String template = this.messageSource.getMessage("subscription.config.template", context);
            final MessageFormat format = new MessageFormat(template);
            final String message = context.getSubscriptionConfig().toString(format);
            return CommandResult.withMessage(message);
        }
    }
}
