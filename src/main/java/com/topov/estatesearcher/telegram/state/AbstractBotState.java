package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.Keyboard;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractBotState implements BotState {
    private final StateName stateName;

    protected AbstractBotState(StateName stateName) {
        this.stateName = stateName;
    }

    @Override
    public Keyboard createKeyboard(Update update) {
        return new Keyboard();
    }
}
