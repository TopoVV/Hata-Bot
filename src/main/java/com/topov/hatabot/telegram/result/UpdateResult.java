package com.topov.hatabot.telegram.result;

import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.response.BotResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Getter
public class UpdateResult {
    private final String messageKey;
    private final Object[] messageArgs;

    public UpdateResult(String messageKey) {
        this.messageKey = messageKey;
        this.messageArgs = new Object[] {};
    }

    public UpdateResult(String messageKey, Object... messageArgs) {
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    public String stringify(MessageSourceAdapter messageSource, UserContext context) {
        return messageSource.getMessage(messageKey, context, messageArgs);
    }
}
