package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Getter
public abstract class AbstractBotState implements BotState {
    private final BotStateName botStateName;

    protected List<String> supportedCommands;

    protected AbstractBotState(BotStateName botStateName) {
        this.botStateName = botStateName;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }

    protected boolean isSupportedCommand(String text) {
        return text.startsWith("/") && this.supportedCommands.contains(text);
    }
}
