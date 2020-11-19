package com.topov.estatesearcher.telegram.state.initial;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.executor.CommandExecutor;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.CommandExecutorProvider;
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
    private final UpdateResultFactory updateResultFactory;
    private final CommandExecutorProvider executorProvider;

    private final List<String> supportedCommands = Lists.newArrayList("/subscribe", "/subscriptions");

    @Autowired
    public InitialBotState(SubscriptionService subscriptionService,
                           UpdateResultFactory updateResultFactory,
                           CommandExecutorProvider executorProvider) {
        super(StateName.INITIAL);
        this.subscriptionService = subscriptionService;
        this.updateResultFactory = updateResultFactory;
        this.executorProvider = executorProvider;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final Long chatId = update.getMessage().getChatId();
        final String text = update.getMessage().getText();

        if (text.startsWith("/") && this.supportedCommands.contains(text)) {
            this.executorProvider.getExecutor(text)
                .ifPresent(executor -> executor.execute(update));
        }

        return new UpdateResult("sakaka");
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

//    private UpdateResult handleSubscribeCommand(long chatId) {
//        this.stateEvaluator.setStateForUser(chatId, StateName.SUBSCRIPTION);
//        return this.updateResultFactory.createUpdateResult("replies.subscribe", "commands.subscribe");
//    }
//
//    private UpdateResult handleMySubscriptionsCommand(long chatId) {
//        final List<Subscription> subscriptions = this.subscriptionService.getAllSubscriptionsForUser(chatId);
//        StringBuilder subscriptionsInfo = new StringBuilder();
//        subscriptions.stream()
//            .map(Subscription::toString)
//            .forEach(info -> subscriptionsInfo.append(String.format("\t%s\n", info)));
//
//        final String info = subscriptionsInfo.toString();
//        return this.updateResultFactory.createUpdateResult("replies.mySubscriptions", new Object[] { info });
//    }
//
//    private UpdateResult handleUnsubscribeCommand(long chatId) {
//        this.stateEvaluator.setStateForUser(chatId, StateName.UNSUBSCRIBE);
//        return this.updateResultFactory.createUpdateResult("replies.unsubscribe");
//    }
}
