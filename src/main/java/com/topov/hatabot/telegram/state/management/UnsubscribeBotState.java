package com.topov.hatabot.telegram.state.management;

import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Log4j2
@TelegramBotState
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
                return new UpdateResult("reply.unsubscribe.not.found", subscriptionId);
            }

            this.subscriptionService.removeSubscription(subscriptionId);
            context.setCurrentStateName(BotStateName.MANAGEMENT);
            return new UpdateResult("reply.unsubscribe.success", subscriptionId);
        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            return new UpdateResult("reply.unsubscribe.invalid.input", text);
        }
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        final List<Subscription> subscriptions = this.subscriptionService.getUserSubscriptions(context.getUserId());
        return super.onMy(command, context, subscriptions);
    }

    @CommandMapping(forCommand = "/back")
    public void onBack(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.MANAGEMENT);
    }

}
