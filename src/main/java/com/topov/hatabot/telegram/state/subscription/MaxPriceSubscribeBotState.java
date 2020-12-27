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
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;

@Log4j2
@TelegramBotState
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
            if (maxPrice < 0) {
                return new UpdateResult("reply.price.invalid.input", text);
            }
            final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
            subscriptionConfig.setMaxPrice(maxPrice);
            context.setSubscriptionConfig(new SubscriptionConfig(subscriptionConfig));
            context.setCurrentStateName(BotStateName.SUBSCRIBE);

            return new UpdateResult("reply.max.price", maxPrice);
        } catch (NumberFormatException e) {
            log.error("Invalid price: {}", text);
            return new UpdateResult("reply.price.invalid.input", text);
        }
    }

    @CommandMapping(forCommand = "/back")
    public void onBack(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
    }

    @Override
    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        return super.onCurrent(command, context);
    }
}
