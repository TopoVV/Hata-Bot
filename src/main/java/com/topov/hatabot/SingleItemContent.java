package com.topov.hatabot;

import com.topov.hatabot.message.converter.ContentConverter;
import com.topov.hatabot.telegram.context.UserContext;

public class SingleItemContent<T> implements Content {
    private final T item;

    public SingleItemContent(T item) {
        this.item = item;
    }

    @Override
    public String stringify(UserContext context, ContentConverter converter) {
        return converter.mapContentToString(context, item);
    }
}
