package com.topov.estatesearcher.telegram.state.management;

import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/help")
})
public class UnsubscribeBotState extends AbstractBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public UnsubscribeBotState(SubscriptionService subscriptionService) {
        super(BotStateName.UNSUBSCRIBE);
        this.subscriptionService = subscriptionService;
    }

    @Override
    public String getEntranceMessage() {
        return "UNSUBSCRIBE BOT STATE";
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        return new UpdateResult("UNSUBSCRIBE BOT STATE");
    }


    //    private final SubscriptionService subscriptionService;
//    private final UpdateResultFactory updateResultFactory;
//
//
//
//    @Override
//    public UpdateResult handleUpdate(Update update) {
//        final Long chatId = update.getMessage().getChatId();
//        final String text = update.getMessage().getText();
//
//        try {
//            final long subscriptionId = Long.parseLong(text);
//            final Optional<Subscription> subscription = this.subscriptionService.findSubscription(subscriptionId, chatId);
//
//            if (subscription.isPresent()) {
//                this.subscriptionService.removeSubscription(subscriptionId);
//                this.stateEvaluator.setStateForUser(chatId, StateName.INITIAL);
//
//                return this.updateResultFactory.createUpdateResult("replies.unsubscribe.success");
//            }
//
//            return this.updateResultFactory.createUpdateResult("replies.unsubscribe.fail.notFound");
//        } catch (NumberFormatException e) {
//           return this.updateResultFactory.createUpdateResult("replies.unsubscribe.fail.invalidInput", new Object[] { text });
//       }
//    }
}
