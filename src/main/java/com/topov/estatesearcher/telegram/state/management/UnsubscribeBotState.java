package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/my")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/my" }),
})
public class UnsubscribeBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService, MessageSourceAdapter messageSource) {
        super(BotStateName.UNSUBSCRIBE, "unsubscribe.header", "unsubscribe.commands", messageSource);
        this.subscriptionService = subscriptionService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        final String chatId = context.getChatId();
        final String text = update.getText();

        try {
            final long subscriptionId = Long.parseLong(text);
            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, chatId);

            if (subscription.isPresent()) {
                this.subscriptionService.removeSubscription(subscriptionId);
                context.setCurrentStateName(BotStateName.MANAGEMENT);
                return UpdateResult.withMessage("The subscription has been deleted.");
            }

            return UpdateResult.withMessage("Not found. Try another id.");
        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            return UpdateResult.withMessage("Invalid id");
        }
    }

    private String getSubscriptionsInfo(String chatId) {
        return this.subscriptionService.getAllSubscriptionsForUser(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        log.info("Executing /my command for user {}", context.getChatId());
        final String chatId = context.getChatId();
        final String subscriptions = getSubscriptionsInfo(chatId);
        return CommandResult.withMessage(String.format("Your subscriptions:\n\n%s", subscriptions));
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getChatId());
        context.setCurrentStateName(BotStateName.MANAGEMENT);
        return CommandResult.empty();
    }


}
