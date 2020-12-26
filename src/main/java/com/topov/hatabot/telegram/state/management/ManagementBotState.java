package com.topov.hatabot.telegram.state.management;

import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log4j2
@TelegramBotState
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/my" }),
    @KeyboardRow(buttons = { "/unsubscribe" }),
    @KeyboardRow(buttons = { "/main", "/donate", "/language" }),
})
public class ManagementBotState extends AbstractManagementBotState {

    @Autowired
    public ManagementBotState(SubscriptionService subscriptionService) {
        super(StateUtils.MANAGEMENT_PROPS, subscriptionService);
    }

    @CommandMapping(forCommand = "/unsubscribe")
    public void onUnsubscribe(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.UNSUBSCRIBE);
    }

    @CommandMapping(forCommand = "/main")
    public void onMain(TelegramCommand command, UserContext context) {
        final DefaultMainExecutor executor = new DefaultMainExecutor();
        executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/language" )
    public void onLanguage(TelegramCommand command, UserContext context) {
        final DefaultLanguageExecutor executor = new DefaultLanguageExecutor();
        executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/my")
    public CommandResult onMy(TelegramCommand command, UserContext context) {
        final List<Subscription> subscriptions = this.subscriptionService.getUserSubscriptions(context.getUserId());
        final DefaultMyExecutor executor = new DefaultMyExecutor();
        return executor.execute(command, context, subscriptions);
    }

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        final DefaultDonateExecutor executor = new DefaultDonateExecutor();
        return executor.execute(command, context);
    }

}
