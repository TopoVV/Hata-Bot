package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserContext {
    private final Long chatId;
    @Setter
    private BotStateName stateName;

    public UserContext(Long chatId, BotStateName stateName) {
        this.chatId = chatId;
        this.stateName = stateName;
    }
}
