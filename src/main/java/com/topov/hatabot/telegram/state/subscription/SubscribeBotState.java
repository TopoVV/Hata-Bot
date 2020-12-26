package com.topov.hatabot.telegram.state.subscription;

import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.service.SubscriptionService;
import com.topov.hatabot.telegram.context.SubscriptionConfig;
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

@Log4j2
@TelegramBotState
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/minPrice", "/maxPrice", "/city" }),
    @KeyboardRow(buttons = { "/current", "/cancel", "/save" }),
    @KeyboardRow(buttons = { "/main", "/language", "/donate" })
})
public class SubscribeBotState extends AbstractSubscribeBotState {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscribeBotState(SubscriptionService subscriptionService) {
        super(StateUtils.SUBSCRIBE_PROPS);
        this.subscriptionService = subscriptionService;
    }

    @CommandMapping(forCommand = "/minPrice")
    public void  onMinPrice(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MIN_PRICE);
    }

    @CommandMapping(forCommand = "/maxPrice")
    public void onMaxPrice(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MAX_PRICE);
    }

    @CommandMapping(forCommand = "/city")
    public void onCity(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_CITY);
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

    @CommandMapping(forCommand = "/donate")
    public CommandResult onDonate(TelegramCommand command, UserContext context) {
        final DefaultDonateExecutor executor = new DefaultDonateExecutor();
        return executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/save")
    public CommandResult onSave(TelegramCommand command, UserContext context) {
        final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
        if (subscriptionConfig.isConfigured()) {
            context.resetSubscriptionConfig();
            this.subscriptionService.saveSubscription(new Subscription(subscriptionConfig));
            return new CommandResult("reply.save.success");
        }

        return new CommandResult("reply.save.not.created");
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult onCancel(TelegramCommand command, UserContext context) {
        context.resetSubscriptionConfig();
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        return new CommandResult("reply.cancel");
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        final DefaultCurrentExecutor executor = new DefaultCurrentExecutor();
        return executor.execute(command, context);
    }
}
