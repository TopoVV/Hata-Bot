package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.command.handler.CommandHandler;
import com.topov.estatesearcher.telegram.state.command.handler.CommandInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    protected String getMessage(String key, UserContext context) {
        return this.messageSource.getMessage(key, context);
    }

    protected String getMessage(String key, UserContext context, Object ... args) {
        return this.messageSource.getMessage(key, context, args);
    }

    public final BotStateName getStateName() {
        return this.props.getStateName();
    }

    public interface DefaultExecutor {
        CommandResult execute(TelegramCommand command, UserContext context);
    }

    public static class DefaultMainExecutor implements DefaultExecutor {
        @Override
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.MAIN);
            return CommandResult.empty();
        }
    }

    public static class DefaultLanguageExecutor implements DefaultExecutor {
        @Override
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
            return CommandResult.empty();
        }
    }

    public static class DefaultDonateExecutor implements DefaultExecutor {
        private final MessageSourceAdapter messageSource;

        public DefaultDonateExecutor(MessageSourceAdapter messageSource) {
            this.messageSource = messageSource;
        }

        @Override
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.DONATE);
            final String message = this.messageSource.getMessage("main.donate.reply", context);
            return CommandResult.withMessage(message);
        }
    }
}
