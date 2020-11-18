package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UpdateResultFactoryImpl implements UpdateResultFactory {
    private final MessageSource messageSource;

    @Autowired
    public UpdateResultFactoryImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public UpdateResult createUpdateResult(String messageCode) {
        final String message = messageSource.getMessage(messageCode, null, Locale.ENGLISH);
        return new UpdateResult(message);
    }

    @Override
    public UpdateResult createUpdateResult(String messageCode, Object[] ... args) {
        final String message = messageSource.getMessage(messageCode, args[0], Locale.ENGLISH);
        return new UpdateResult(String.format("%s", message));
    }

    @Override
    public UpdateResult createUpdateResult(String messageCode, String commandsCode) {
        final String message = messageSource.getMessage(messageCode, null, Locale.ENGLISH);
        final String commands = messageSource.getMessage(commandsCode, null, Locale.ENGLISH);
        return new UpdateResult(String.format("%s\n\n%s", message, commands));
    }
}
