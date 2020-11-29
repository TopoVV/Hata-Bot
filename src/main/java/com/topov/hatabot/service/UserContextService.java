package com.topov.hatabot.service;

import com.topov.hatabot.telegram.context.UserContext;

public interface UserContextService {
    void setContext(UserContext userContext);
    UserContext getContextForUser(String userId);
}
