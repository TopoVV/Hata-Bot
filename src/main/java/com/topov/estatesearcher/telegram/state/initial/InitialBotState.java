package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.service.UserContextService;
import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

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
    public InitialBotState(UserContextService contextService) {
        super(BotStateName.INITIAL);
        this.contextService = contextService;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        final Keyboard keyboard = new Keyboard();
        keyboard.addOneButton(new KeyboardButton("/subscribe"));
        keyboard.addOneButton(new KeyboardButton("/subscriptions"));

        return keyboard;
    }

    @Override
    public String getEntranceMessage() {
        return "INITIAL BOT STATE";
    }

    @CommandMapping(forCommand = "/start")
    public CommandResult handleStart(TelegramCommand command) {
        log.info("Executing /start command");
        contextService.createContext(command.getChatId());
        return new CommandResult(BotStateName.INITIAL, "Welcome");
    }

    @CommandMapping(forCommand = "/subscribe")
    public CommandResult handleSubscribe(TelegramCommand command) {
        log.info("Executing /subscribe command");
        return new CommandResult(BotStateName.SUBSCRIPTION, "/subscribe command executed");
    }

    @CommandMapping(forCommand = "/subscriptions" )
    public CommandResult commandHandler(TelegramCommand command) {
        log.info("Executing /subscriptions command");
        return new CommandResult(BotStateName.MANAGEMENT, "/subscriptions command executed");
    }
}
