package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.context.SubscriptionConfig;
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

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/current")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/current" }),
})
public class MinPriceSubscribeBotState extends AbstractSubscribeBotState {
    public MinPriceSubscribeBotState() {
        super(StateUtils.MIN_PRICE_PROPS);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        log.debug("Handling min price update");
        final String text = update.getText();

        try {
            final Integer minPrice = Integer.parseInt(text);
            final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
            subscriptionConfig.setMinPrice(minPrice);
            context.setSubscriptionConfig(new SubscriptionConfig(subscriptionConfig));
            context.setCurrentStateName(BotStateName.SUBSCRIBE);

            final String current = MessageHelper.subscriptionConfigToMessage(subscriptionConfig, context);
            final String message = MessageHelper.getMessage("reply.min.price", context, current, minPrice);

            return UpdateResult.withMessage(message);
        } catch (NumberFormatException e) {
            log.error("Invalid price: {}", text);
            final String message = MessageHelper.getMessage("reply.price.invalid.input", context, text);
            return UpdateResult.withMessage(message);
        }
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getUserId());
        final DefaultCurrentExecutor executor = new DefaultCurrentExecutor();
        return executor.execute(command, context);
    }
}
