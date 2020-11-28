package com.topov.estatesearcher.utils;

import com.topov.estatesearcher.adapter.MessageSourceAdapter;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.telegram.context.SubscriptionConfig;
import com.topov.estatesearcher.telegram.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Helper class needed to retrieve proper message and templates from ResourceBundle depending on locale from any place
 * inside the application.
 */
@Component
public class MessageHelper extends ResourceBundleMessageSource {
    private static MessageSourceAdapter messageSource;

    @Autowired
    public MessageHelper(MessageSourceAdapter messageSource) {
        MessageHelper.messageSource = messageSource;
    }

    public static String subscriptionConfigToMessage(SubscriptionConfig config, UserContext context) {
        final String template = messageSource.getMessage("subscription.config.template", context);
        final String empty = messageSource.getMessage("subscription.config.props.empty", context);

        final String minPrice = config.getMinPrice().map(String::valueOf).orElse("0");
        final String maxPrice = config.getMaxPrice().map(String::valueOf).orElse("MAX");
        final String cityName = config.getCity().map(City::getCityName).orElse(empty);

        return MessageFormat.format(template, minPrice, maxPrice, cityName, empty);
    }

    public static String subscriptionToMessage(Subscription subscription, UserContext context) {
        final String template = messageSource.getMessage("subscription.template", context);
        final String empty = messageSource.getMessage("subscription.config.props.empty", context);
        final String anyCity = messageSource.getMessage("subscription.city.any", context);

        final String id = subscription.getSubscriptionId().toString();
        final String minPrice = subscription.getMinPrice().toString();
        final Integer maxPriceValue = subscription.getMaxPrice();
        final String maxPrice = maxPriceValue.equals(Integer.MAX_VALUE) ? "MAX" : maxPriceValue.toString();
        final String cityName = subscription.getCity().map(City::getCityName).orElse(anyCity);

        return MessageFormat.format(template, id, minPrice, maxPrice, cityName, empty);
    }

    public static String getMessage(String templateKey, UserContext context, Object ... args) {
        return messageSource.getMessage(templateKey, context, args);
    }
}
