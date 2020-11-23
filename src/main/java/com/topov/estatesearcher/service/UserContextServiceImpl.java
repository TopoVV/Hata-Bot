package com.topov.estatesearcher.service;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class UserContextServiceImpl implements UserContextService {
    private final Map<String, UserContext> userContexts = new HashMap<>();

    @Override
    public void setContext(UserContext userContext) {
        this.userContexts.put(userContext.getChatId(), userContext);
    }

    @Override
    public UserContext getContextForUser(String chatId) {
        return this.userContexts.getOrDefault(chatId, new UserContext(chatId, BotStateName.ANONYMOUS));
    }

    @Override
    public void createContext(String chatId) {
        this.userContexts.put(chatId, new UserContext(chatId, BotStateName.MAIN));
    }
}
