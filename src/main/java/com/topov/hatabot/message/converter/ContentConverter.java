package com.topov.hatabot.message.converter;

import com.topov.hatabot.Content;
import com.topov.hatabot.telegram.context.UserContext;

public interface ContentConverter {
    <T> String mapContentToString(UserContext context, T content);
}
