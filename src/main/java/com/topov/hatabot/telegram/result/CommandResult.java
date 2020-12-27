package com.topov.hatabot.telegram.result;

import com.topov.hatabot.Content;
import com.topov.hatabot.message.converter.ContentConverter;
import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.response.BotResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class CommandResult {
    private String messageKey;
    private Content content;

    public CommandResult(String messageKey) {
        this.messageKey = messageKey;
    }
    public CommandResult(String messageKey, Content content) {
        this.messageKey = messageKey;
        this.content = content;
    }
    public Optional<String> stringify(MessageSourceAdapter messageSource, ContentConverter converter, UserContext context) {
        return getMessageKey()
            .map(key -> messageSource.getMessage(key, context))
            .map(message -> {
                final String content = getContent()
                    .map(c -> c.stringify(context, converter))
                    .orElse("");

                return message.concat(content);
            });
    }

    private Optional<String> getMessageKey() {
        return Optional.ofNullable(this.messageKey);
    }

    private Optional<Content> getContent() {
        return Optional.ofNullable(content);
    }
}
