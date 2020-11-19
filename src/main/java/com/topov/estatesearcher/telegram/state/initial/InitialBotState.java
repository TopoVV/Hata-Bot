package com.topov.estatesearcher.telegram.state.initial;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.provider.CommandExecutorProvider;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@Log4j2
@Service
public class InitialBotState extends AbstractBotState {
    private final CommandExecutorProvider executorProvider;

    @Autowired
    public InitialBotState(CommandExecutorProvider executorProvider) {
        super(BotStateName.INITIAL);
        this.executorProvider = executorProvider;
        this.supportedCommands = Lists.newArrayList("/subscribe", "/subscriptions");
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        final String text = update.getMessage().getText();

        if (text.startsWith("/") && this.supportedCommands.contains(text)) {
            this.executorProvider.getExecutor(text).ifPresent(executor -> executor.execute(update));
        }

        return new UpdateResult("INITIAL BOT STATE");
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

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

}
