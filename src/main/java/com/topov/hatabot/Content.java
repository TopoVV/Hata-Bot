package com.topov.hatabot;

import com.topov.hatabot.message.converter.ContentConverter;
import com.topov.hatabot.telegram.context.UserContext;

public interface Content {
    String stringify(UserContext context, ContentConverter converter);
}
