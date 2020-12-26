package com.topov.hatabot.message.source;

import com.topov.hatabot.telegram.context.UserContext;

public interface MessageSourceAdapter {
    String getMessage(String key, UserContext context);
    String getMessage(String key, UserContext context, Object ... args);
}
