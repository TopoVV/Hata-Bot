package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.command.CommandHandler;
import com.topov.estatesearcher.telegram.command.CommandInfo;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.EntranceMessage;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.utils.MessageHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Getter
public abstract class AbstractBotState implements BotState {
    private final Map<CommandInfo, CommandHandler> handlers;
    private final StateProperties props;

    @Setter
    private Keyboard keyboard;

    protected AbstractBotState(StateProperties props) {
        this.props = props;
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
        final String header = MessageHelper.getMessage(headerKey, context);
        final String commands = MessageHelper.getMessage(commandsKey, context);
        final String entranceText = MessageHelper.getMessage("entrance.template", context, header, commands);
        final EntranceMessage entranceMessage = new EntranceMessage(context.getUserId(), entranceText, this.keyboard);
        return Optional.of(entranceMessage);
    }

    public final BotStateName getStateName() {
        return this.props.getStateName();
    }

    public static class DefaultMainExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.MAIN);
            return CommandResult.empty();
        }
    }

    public static class DefaultLanguageExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
            return CommandResult.empty();
        }
    }

    public static class DefaultDonateExecutor {
        public CommandResult execute(TelegramCommand command, UserContext context) {
            context.resetSubscriptionConfig();
            context.setCurrentStateName(BotStateName.DONATE);
            final String message = MessageHelper.getMessage("reply.donate", context);
            return CommandResult.withMessage(message);
        }
    }
}
