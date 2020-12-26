package com.topov.hatabot.utils;

import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.telegram.context.SubscriptionConfig;
import com.topov.hatabot.telegram.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Helper class needed to retrieve proper message and templates from ResourceBundle depending on locale from any place
 * inside the application.
 */
@Component
public class MessageHelper extends ResourceBundleMessageSource {
    private static MessageSourceAdapter messageSourceAdapter;

    @Autowired
    public MessageHelper(MessageSourceAdapter messageSourceAdapter) {
        MessageHelper.messageSourceAdapter = messageSourceAdapter;
    }

    public static String subscriptionConfigToMessage(SubscriptionConfig config, UserContext context) {
        return null;
    }

    public static String subscriptionToMessage(Subscription subscription, UserContext context) {
        return null;
    }

    public static String getMessage(String templateKey, UserContext context, Object ... args) {
        return messageSourceAdapter.getMessage(templateKey, context, args);
    }
}
