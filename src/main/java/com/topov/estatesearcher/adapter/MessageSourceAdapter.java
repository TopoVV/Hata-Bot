package com.topov.estatesearcher.adapter;

import com.topov.estatesearcher.telegram.context.UserContext;

public interface MessageSourceAdapter {
    String getMessage(String key, UserContext context);
    String getMessage(String key, UserContext context, Object ... args);
}
