package com.topov.hatabot.service;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class UserContextServiceImpl implements UserContextService {
    private final Map<String, UserContext> userContexts = new HashMap<>();

    @Override
    public void setContext(UserContext userContext) {
        this.userContexts.put(userContext.getUserId(), userContext);
    }

    @Override
    public UserContext getContextForUser(String userId) {
        return this.userContexts.getOrDefault(userId, new UserContext(userId, BotStateName.ANONYMOUS));
    }
}
