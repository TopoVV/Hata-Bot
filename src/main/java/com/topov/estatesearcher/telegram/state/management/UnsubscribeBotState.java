package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.TelegramUpdate;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help")
})
public class UnsubscribeBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService, MessageSource messageSource) {
        super(BotStateName.UNSUBSCRIBE, messageSource);
        this.subscriptionService = subscriptionService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        final Long chatId = update.getChatId();
        final String text = update.getText();

        try {
            final long subscriptionId = Long.parseLong(text);
            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, chatId);

            if (subscription.isPresent()) {
                this.subscriptionService.removeSubscription(subscriptionId);
                changeState.accept(BotStateName.MANAGEMENT);
                return new UpdateResult(chatId, "The subscription has been deleted");
            }
            return new UpdateResult(chatId, "Not found. Try another id or tap /help.");

        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            return new UpdateResult(chatId, "Invalid id");
        }
    }
}
