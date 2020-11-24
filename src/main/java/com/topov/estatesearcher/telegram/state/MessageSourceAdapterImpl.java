package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.context.UserContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Log4j2
@Service
public class MessageSourceAdapterImpl implements MessageSourceAdapter {
    private final MessageSource messageSource;

    @Autowired
    public MessageSourceAdapterImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key, UserContext context) {
        return this.messageSource.getMessage(key, null, context.getLocale());
    }

    @Override
    public String getMessage(String key, UserContext context, Object... args) {
        return this.messageSource.getMessage(key, args, context.getLocale());
    }
}
