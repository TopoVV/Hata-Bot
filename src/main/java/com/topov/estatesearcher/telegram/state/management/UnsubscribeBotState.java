package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.model.SubscriptionList;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.utils.MessageHelper;
import com.topov.estatesearcher.utils.StateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/my")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/my" }),
    @KeyboardRow(buttons = { "/back" })
})
public class UnsubscribeBotState extends AbstractManagementBotState {

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService) {
        super(StateUtils.UNSUBSCRIBE_PROPS, subscriptionService);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        final String userId = context.getUserId();
        final String text = update.getText();

        try {
            final long subscriptionId = Long.parseLong(text);
            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, userId);

            if (!subscription.isPresent()) {
                final String message = MessageHelper.getMessage("reply.unsubscribe.not.found", context, subscriptionId);
                return UpdateResult.withMessage(message);
            }
            this.subscriptionService.removeSubscription(subscriptionId);
            context.setCurrentStateName(BotStateName.MANAGEMENT);
            final String message = MessageHelper.getMessage("reply.unsubscribe.success", context, subscriptionId);
            return UpdateResult.withMessage(message);

        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            final String message = MessageHelper.getMessage("reply.unsubscribe.invalid.input", context, text);
            return UpdateResult.withMessage(message);
        }
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        log.info("Executing /my command for user {}", context.getUserId());
        final SubscriptionList subscriptions = this.subscriptionService.getUserSubscriptions(context.getUserId());
        final DefaultMyExecutor executor = new DefaultMyExecutor();
        return executor.execute(command, context, subscriptions);
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }

}
