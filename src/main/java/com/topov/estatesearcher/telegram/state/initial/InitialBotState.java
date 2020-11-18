package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.BotStateEvaluator;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;

@Log4j2
@Service
public class InitialBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public InitialBotState(BotStateEvaluator stateEvaluator, SubscriptionService subscriptionService) {
        super(StateName.INITIAL, stateEvaluator);
        this.subscriptionService = subscriptionService;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        switch (text) {
            case "/start": return new UpdateResult("Welcome");
            case "/subscribe": return handleSubscribeCommand(chatId);
            case "/my_subscriptions": return handleMySubscriptionsCommand(chatId);
            case "/unsubscribe": return handleUnsubscribeCommand(chatId);
            default: return new UpdateResult("The command not supported");
        }
    }

    @Override
    public Hint getHint(Update update) {
        final Hint hint = new Hint();
        hint.appendHintMessage("/subscribe - create a subscription\n");
        hint.appendHintMessage("/my_subscriptions - see subscriptions\n");
        hint.appendHintMessage("/unsubscribe - delete a subscription\n");

        return hint;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/my_subscriptions"));
        keyboard.addOneButton(new KeyboardButton("/unsubscribe"));

        return keyboard;
    }

    private UpdateResult handleSubscribeCommand(long chatId) {
        this.stateEvaluator.setStateForUser(chatId, StateName.SUBSCRIPTION);
        return new UpdateResult("Let's subscribe you");
    }

    private UpdateResult handleMySubscriptionsCommand(long chatId) {
        final List<Subscription> subscriptions = this.subscriptionService.getAllSubscriptionsForUser(chatId);
        StringBuilder subscriptionsInfo = new StringBuilder();
        subscriptions.stream()
            .map(Subscription::toString)
            .forEach(info -> subscriptionsInfo.append(String.format("\t%s\n", info)));

        final String info = subscriptionsInfo.toString();
        return new UpdateResult("Your subscriptions: \n" + info);
    }

    private UpdateResult handleUnsubscribeCommand(long chatId) {
        this.stateEvaluator.setStateForUser(chatId, StateName.UNSUBSCRIBE);
        return new UpdateResult("UNSUBSCRIBE");
    }
}
