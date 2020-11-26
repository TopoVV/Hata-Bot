package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.StateUtils;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/my")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/my" }),
})
public class UnsubscribeBotState extends AbstractManagementBotState {

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService, MessageSourceAdapter messageSource) {
        super(StateUtils.UNSUBSCRIBE_PROPS, messageSource, subscriptionService);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        final String chatId = context.getChatId();
        final String text = update.getText();

        try {
            final long subscriptionId = Long.parseLong(text);
            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, chatId);

            if (!subscription.isPresent()) {
                final String message = getMessage("unsubscribe.notFound.reply", context, subscriptionId);
                return UpdateResult.withMessage(message);
            }
            this.subscriptionService.removeSubscription(subscriptionId);
            context.setCurrentStateName(BotStateName.MANAGEMENT);
            final String message = getMessage("unsubscribe.success.reply", context, subscriptionId);
            return UpdateResult.withMessage(message);

        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            final String message = getMessage("unsubscribe.invalidInput.reply", context, text);
            return UpdateResult.withMessage(message);
        }
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        log.info("Executing /my command for user {}", context.getChatId());
        final DefaultExecutor executor = new DefaultMyExecutor(this.messageSource, this.subscriptionService);
        return executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return CommandResult.empty();
    }

}
