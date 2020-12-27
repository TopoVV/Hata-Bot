package com.topov.hatabot.telegram.state;

import com.topov.hatabot.telegram.command.CommandHandler;
import com.topov.hatabot.telegram.command.CommandInfo;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.Keyboard;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.request.UpdateWrapper;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.EntranceMessage;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.utils.MessageHelper;
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
        return new UpdateResult("update.not.supported");
    }

    @Override
    public CommandResult executeCommand(TelegramCommand command, UserContext context) {
        if (this.handlers.containsKey(command.getCommand())) {
            return this.handlers.get(command.getCommand()).handle(command, context);
        } else {
            return new CommandResult("command.not.supported");
        }
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        final String headerKey = this.props.getHeaderKey();
        final String commandsKey = this.props.getCommandsKey();
        final String header = MessageHelper.getMessage(headerKey, context);
        final String commands = MessageHelper.getMessage(commandsKey, context);
        final String entranceText = MessageHelper.getMessage("entrance.template", context, header, commands);
        return Optional.of(new EntranceMessage(context.getUserId(), entranceText, this.keyboard));
    }

    public final BotStateName getStateName() {
        return this.props.getStateName();
    }

    protected void onMain(TelegramCommand command, UserContext context) {
        context.resetSubscriptionConfig();
        context.setCurrentStateName(BotStateName.MAIN);
    }

    protected void onLanguage(TelegramCommand command, UserContext context) {
        context.resetSubscriptionConfig();
        context.setCurrentStateName(BotStateName.CHOOSE_LANGUAGE);
    }

    protected CommandResult onDonate(TelegramCommand command, UserContext context) {
        context.resetSubscriptionConfig();
        context.setCurrentStateName(BotStateName.DONATE);
        return new CommandResult("reply.donate");
    }
}
