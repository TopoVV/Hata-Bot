package com.topov.estatesearcher.service;

import com.topov.estatesearcher.telegram.context.UserContext;

public interface UserContextService {
    void setContext(UserContext userContext);
    UserContext getContextForUser(Long chatId);
    void createContext(Long chatId);
}