package com.topov.hatabot.message.stringifier;

import com.topov.hatabot.telegram.context.UserContext;

public interface ContentStringifier<T> {
    String convert(UserContext context, T toConvert);
    Class<?> stringifiedType();
}
