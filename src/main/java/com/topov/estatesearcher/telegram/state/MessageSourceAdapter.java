package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.context.UserContext;

public interface MessageSourceAdapter {
    String getMessage(String key, UserContext context);
    String getMessage(String key, UserContext context, Object ... args);
}
