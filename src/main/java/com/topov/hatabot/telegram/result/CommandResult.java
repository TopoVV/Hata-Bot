package com.topov.hatabot.telegram.result;

import com.topov.hatabot.Content;
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

    public Optional<String> getMessageKey() {
        return Optional.ofNullable(this.messageKey);
    }

    public Optional<Content> getContent() {
        return Optional.ofNullable(content);
    }
}
