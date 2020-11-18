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

        if (text.equals("/start")) {
            return new UpdateResult("Welcome");
        } else if (text.equals("/subscribe")) {
            this.stateEvaluator.setStateForUser(chatId, StateName.SUBSCRIPTION);
            return new UpdateResult("Let's subscribe you");
        } else if (text.equals("/my_subscriptions")) {
            final List<Subscription> subscriptions = this.subscriptionService.getAllSubscriptionsForUser(chatId);
            StringBuilder subscriptionsInfo = new StringBuilder();
            subscriptions.stream()
                .map(Subscription::toString)
                .forEach(info -> subscriptionsInfo.append(String.format("\t%s\n", info)));

            final String info = subscriptionsInfo.toString();
            return new UpdateResult("Your subscriptions: \n" + info);
        } else {
            return new UpdateResult("The command not supported");
        }
    }

    @Override
    public Hint getHint(Update update) {
        return new Hint("/subscribe - create a subscription\n/my_subscriptions - see subscriptions");
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/my_subscriptions"));
        return keyboard;
    }
}
