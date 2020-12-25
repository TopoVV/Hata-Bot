package com.topov.hatabot.telegram.state.subscription;

import com.topov.hatabot.telegram.context.SubscriptionConfig;
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

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/current")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/current" }),
})
public class MaxPriceSubscribeBotState extends AbstractSubscribeBotState {
    public MaxPriceSubscribeBotState() {
        super(StateUtils.MAX_PRICE_PROPS);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        log.debug("Handling max price update");
        final String text = update.getText();

        try {
            final Integer maxPrice = Integer.parseInt(text);
            final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
            subscriptionConfig.setMaxPrice(maxPrice);
            context.setSubscriptionConfig(new SubscriptionConfig(subscriptionConfig));
            context.setCurrentStateName(BotStateName.SUBSCRIBE);

            final String current = MessageHelper.subscriptionConfigToMessage(subscriptionConfig, context);
            final String message = MessageHelper.getMessage("reply.max.price", context, current, maxPrice);
            return UpdateResult.withMessage(message);
        } catch (NumberFormatException e) {
            log.error("Invalid price: {}", text);
            final String message = MessageHelper.getMessage("reply.price.invalid.input", context, text);
            return UpdateResult.withMessage(message);
        }
    }

    @CommandMapping(forCommand = "/back")
    public void onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
    }

    @CommandMapping(forCommand = "/current")
    public String onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getUserId());
        final DefaultCurrentExecutor executor = new DefaultCurrentExecutor();
        return executor.execute(command, context);
    }

}
