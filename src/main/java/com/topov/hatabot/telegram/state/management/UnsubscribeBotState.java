package com.topov.hatabot.telegram.state.management;

import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.model.SubscriptionList;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.AcceptedCommand;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
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
