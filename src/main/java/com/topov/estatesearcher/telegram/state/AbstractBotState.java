package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.handler.CommandHandler;
import com.topov.estatesearcher.telegram.state.handler.CommandInfo;
import com.topov.estatesearcher.telegram.state.subscription.CommandExecutor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final Map<CommandInfo, CommandHandler> handlers;
    private final StateProperties props;

    protected final MessageSourceAdapter messageSource;

    @Setter
    private Keyboard keyboard;

    protected AbstractBotState(StateProperties props, MessageSourceAdapter messageSource) {
        this.props = props;
        this.messageSource = messageSource;
        this.handlers = new HashMap<>();
    }

    public void addCommandHandler(CommandHandler commandHandler, CommandInfo commandInfo) {
        this.handlers.put(commandInfo, commandHandler);
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        return UpdateResult.withMessage("I dont understand!");
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext context) {
        if (this.handlers.containsKey(command.getCommand())) {
            return this.handlers.get(command.getCommand()).act(command, context);
        } else {
            return CommandResult.withMessage("No such command supported");
        }
    }

    protected String getMessage(String key, UserContext context) {
        return this.messageSource.getMessage(key, context);
    }

    protected String getMessage(String key, UserContext context, Object ... args) {
        return this.messageSource.getMessage(key, context, args);
    }

    protected CommandResult defaultMain(TelegramCommand command, UserContext context) {
        return new MainCommandExecutor().executeCommand(command, context);
    }

    protected CommandResult defaultLanguage(TelegramCommand command, UserContext context) {
        return new LanguageCommandExecutor().executeCommand(command, context);
    }

    protected CommandResult defaultDonate(TelegramCommand command, UserContext context) {
        return new DonateCommandExecutor().executeCommand(command, context);
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        final String headerKey = this.props.getHeaderKey();
        final String commandsKey = this.props.getCommandsKey();
        final String header = this.messageSource.getMessage(headerKey, context);
        final String commands = this.messageSource.getMessage(commandsKey, context);
        final String entranceText = this.messageSource.getMessage("entrance.template", context, header, commands);
        final EntranceMessage entranceMessage = new EntranceMessage(context.getChatId(), entranceText, this.keyboard);
        return Optional.of(entranceMessage);
    }

    public final BotStateName getStateName() {
        return this.props.getStateName();
    }

    private final class MainCommandExecutor implements CommandExecutor {
        @Override
        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /main command for user {}", context.getChatId());
            context.setCurrentStateName(BotStateName.MAIN);
            return CommandResult.empty();
        }
    }

    private final class LanguageCommandExecutor implements CommandExecutor {
        @Override
        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /language command for user {}", context.getChatId());
            context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
            return CommandResult.empty();
        }
    }

    private final class DonateCommandExecutor implements CommandExecutor {
        @Override
        public CommandResult executeCommand(TelegramCommand command, UserContext context) {
            log.info("Executing /donate command for user: {}", context.getChatId());
            context.setCurrentStateName(BotStateName.DONATE);
            final String message = getMessage("main.donate.reply", context);
            return CommandResult.withMessage(message);
        }
    }
}
