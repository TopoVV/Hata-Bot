package com.topov.hatabot.telegram.result;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommandResult<T> {
    private T content;
    private boolean isEmpty = true;

    public static CommandResult<String> withMessage(String message) {
        return new CommandResult<>(message);
    }

    public CommandResult(T content) {
        this.content = content;
        this.isEmpty = false;
    }

    public boolean hasContent() {
        return !isEmpty;
    }
}
