package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
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
public class UnsubscribeBotState extends AbstractBotState {
    public static final String ENTRANCE_MESSAGE = "Specify the id of the subscription you want to delete.\n\n" +
        "Commands:\n" +
        "/back- go back to management\n" +
        "/my - list my subscriptions\n";

    private final SubscriptionService subscriptionService;

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService) {
        super(BotStateName.UNSUBSCRIBE);
        this.subscriptionService = subscriptionService;
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command) {
        log.info("Executing /my command for user {}", command.getChatId());
        final Long chatId = command.getChatId();
        final String subscriptions = getSubscriptionsInfo(chatId);
        return CommandResult.withMessage(String.format("Your subscriptions:\n\n%s", subscriptions));
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /back command for user {}", command.getChatId());
        changeState.accept(BotStateName.MANAGEMENT);
        return CommandResult.empty();
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
                return new UpdateResult("The subscription has been deleted.");
            }

            return new UpdateResult("Not found. Try another id.");
        } catch (NumberFormatException e) {
            log.error("Subscription not found", e);
            return new UpdateResult("Invalid id");
        }
    }

    private String getSubscriptionsInfo(Long chatId) {
        return this.subscriptionService.getAllSubscriptionsForUser(chatId).stream()
            .map(Subscription::toString)
            .collect(Collectors.joining("\n----------\n"));
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return ENTRANCE_MESSAGE;
    }
}
