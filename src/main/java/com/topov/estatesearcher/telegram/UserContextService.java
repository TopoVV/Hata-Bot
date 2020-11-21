package com.topov.estatesearcher.telegram;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class UserContextService {
    private final Map<Long, UserContext> userContexts;

    public UserContextService(Map<Long, UserContext> userContexts) {
        this.userContexts = userContexts;
    }

    public void setContext(UserContext userContext) {
        this.userContexts.put(userContext.getChatId(), userContext);
    }

    public UserContext getContextForUser(Long chatId) {
        return this.userContexts.get(chatId);
    }
}
