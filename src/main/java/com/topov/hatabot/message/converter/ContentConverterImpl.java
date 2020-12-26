package com.topov.hatabot.message.converter;

import com.topov.hatabot.message.stringifier.ContentStringifier;
import com.topov.hatabot.telegram.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentConverterImpl implements ContentConverter {
    private final List<ContentStringifier<?>> converters;

    @Autowired
    public ContentConverterImpl(List<ContentStringifier<?>> converters) {
        this.converters = converters;
    }

    @Override
    @SuppressWarnings("unchecked cast")
    public <T> String mapContentToString(UserContext context, T content) {
        final ContentStringifier<T> contentStringifier = (ContentStringifier<T>) converters.stream()
            .filter(converter -> converter.stringifiedType().equals(content.getClass()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

        return contentStringifier.convert(context, content);
    }
}
