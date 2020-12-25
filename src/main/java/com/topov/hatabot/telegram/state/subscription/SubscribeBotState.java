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
import com.topov.hatabot.telegram.state.annotation.AcceptedCommand;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/main"),
    @AcceptedCommand(commandName = "/cancel"),
    @AcceptedCommand(commandName = "/save"),
    @AcceptedCommand(commandName = "/minPrice"),
    @AcceptedCommand(commandName = "/maxPrice"),
    @AcceptedCommand(commandName = "/city"),
    @AcceptedCommand(commandName = "/current"),
    @AcceptedCommand(commandName = "/language"),
    @AcceptedCommand(commandName = "/donate")
})
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
        log.info("Executing /minPrice command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MIN_PRICE);
    }

    @CommandMapping(forCommand = "/maxPrice")
    public void onMaxPrice(TelegramCommand command, UserContext context) {
        log.info("Executing /maxPrice command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_MAX_PRICE);
    }

    @CommandMapping(forCommand = "/city")
    public void onCity(TelegramCommand command, UserContext context) {
        log.info("Executing /city command for user {}", context.getUserId());
        context.setCurrentStateName(BotStateName.SUBSCRIPTION_CITY);
    }

    @CommandMapping(forCommand = "/save")
    public CommandResult<String> onSave(TelegramCommand command, UserContext context) {
    log.info("Executing /save command for user {}", context.getUserId());
        final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
        if (subscriptionConfig.isConfigured()) {
            context.resetSubscriptionConfig();
            this.subscriptionService.saveSubscription(new Subscription(subscriptionConfig));
            final String message = MessageHelper.getMessage("reply.save.success", context);
            return CommandResult.withMessage(message);
        }

        final String message = MessageHelper.getMessage("reply.save.not.created", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/cancel")
    public CommandResult<String> onCancel(TelegramCommand command, UserContext context) {
        log.info("Executing /cancel command for user {}", context.getUserId());
        context.resetSubscriptionConfig();
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
        final String message = MessageHelper.getMessage("reply.cancel", context);
        return CommandResult.withMessage(message);
    }

    @CommandMapping(forCommand = "/current")
    public String onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /current command for user {}", context.getUserId());
        final DefaultCurrentExecutor executor = new DefaultCurrentExecutor();
        return executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/main")
    public void onMain(TelegramCommand command, UserContext context) {
        log.info("Executing /main command for user {}", context.getUserId());
        final DefaultMainExecutor executor = new DefaultMainExecutor();
        executor.execute(command, context);
    }


    @CommandMapping(forCommand = "/language" )
    public void onLanguage(TelegramCommand command, UserContext context) {
        log.info("Executing /language command for user {}", context.getUserId());
        final DefaultLanguageExecutor executor = new DefaultLanguageExecutor();
        executor.execute(command, context);
    }

    @CommandMapping(forCommand = "/donate")
    public String onDonate(TelegramCommand command, UserContext context) {
        log.info("Executing /donate command for user: {}", context.getUserId());
        final DefaultDonateExecutor executor = new DefaultDonateExecutor();
        return executor.execute(command, context);
    }
}
