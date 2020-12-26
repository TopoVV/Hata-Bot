package com.topov.hatabot;

import com.topov.hatabot.message.converter.ContentConverter;
import com.topov.hatabot.telegram.context.UserContext;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class ListItemContent<T> implements Content {
    private final List<T> items;

    public ListItemContent(List<T> items) {
        this.items = items;
    }

    @Override
    public String stringify(UserContext context, ContentConverter converter) {
        return items.stream()
            .map(item -> converter.mapContentToString(context, item))
            .collect(joining("\n"));
    }
}
