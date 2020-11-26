package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.context.SubscriptionConfig;
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

    @Autowired
    public MinPriceSubscribeBotState(MessageSourceAdapter messageSource) {
        super(StateUtils.MIN_PRICE_PROPS, messageSource);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        log.debug("Handling min price update");
        final String chatId = context.getChatId();
        final String text = update.getText();

        try {
            final int minPrice = Integer.parseInt(text);
            final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
            subscriptionConfig.setMinPrice(minPrice);

            context.setCurrentStateName(BotStateName.SUBSCRIBE);

            final String current = subscriptionConfig.toString();
            final String message = getMessage("minPrice.success.reply", context, current);
            return UpdateResult.withMessage(message);
        } catch (NumberFormatException e) {
            log.error("Invalid price: {}", text);
            final String message = getMessage("price.invalidInput.reply", context, text);
            return UpdateResult.withMessage(message);
        }
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getChatId());
        final DefaultExecutor executor = new DefaultCurrentExecutor(this.messageSource);
        return executor.execute(command, context);
    }
}
