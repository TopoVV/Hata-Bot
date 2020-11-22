package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Locale;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/start"),
    @AcceptedCommand(commandName = "/subscribe"),
    @AcceptedCommand(commandName = "/subscriptions")
})
public class InitialBotState extends AbstractBotState {
    private final UserContextService contextService;

    @Autowired
    public InitialBotState(UserContextService contextService, MessageSource messageSource) {
        super(BotStateName.INITIAL, messageSource);
        this.contextService = contextService;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

    @CommandMapping(forCommand = "/start")
    public CommandResult handleStart(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /start command");
        this.contextService.createContext(command.getChatId());
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("initial.welcome", null, Locale.ENGLISH));
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult handleSubscribe(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /subscribe command");
        changeState.accept(BotStateName.SUBSCRIPTION);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("subscription.entrance", null, Locale.ENGLISH));
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public CommandResult commandHandler(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /subscriptions command");
        changeState.accept(BotStateName.MANAGEMENT);
        return new CommandResult(command.getChatId(), this.messageSource.getMessage("management.entrance", null, Locale.ENGLISH));
    }
}
