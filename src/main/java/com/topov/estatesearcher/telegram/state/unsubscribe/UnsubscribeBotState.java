package com.topov.estatesearcher.telegram.state.unsubscribe;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Log4j2
@Service
public class UnsubscribeBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    protected UnsubscribeBotState(BotStateEvaluator botStateEvaluator, SubscriptionService subscriptionService, UpdateResultFactory updateResultFactory) {
        super(StateName.UNSUBSCRIBE, botStateEvaluator);
        this.subscriptionService = subscriptionService;
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        try {
            final long subscriptionId = Long.parseLong(text);
            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, chatId);

            if (subscription.isPresent()) {
                this.subscriptionService.removeSubscription(subscriptionId);
                this.stateEvaluator.setStateForUser(chatId, StateName.INITIAL);

                return this.updateResultFactory.createUpdateResult("replies.unsubscribe.success");
            }

            return this.updateResultFactory.createUpdateResult("replies.unsubscribe.fail.notFound");
        } catch (NumberFormatException e) {
           return this.updateResultFactory.createUpdateResult("replies.unsubscribe.fail.invalidInput", new Object[] { text });
       }
    }
}
