package com.topov.hatabot.message.stringifier;

import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.model.City;
import com.topov.hatabot.telegram.context.SubscriptionConfig;
import com.topov.hatabot.telegram.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class SubscriptionConfigStringifier implements ContentStringifier<SubscriptionConfig> {
    private final MessageSourceAdapter messageSource;

    @Autowired
    public SubscriptionConfigStringifier(MessageSourceAdapter messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String convert(UserContext context, SubscriptionConfig toConvert) {
        final String template = messageSource.getMessage("subscription.config.template", context);
        final String empty = messageSource.getMessage("subscription.config.props.empty", context);

        final String minPrice = toConvert.getMinPrice().map(String::valueOf).orElse("0");
        final String maxPrice = toConvert.getMaxPrice().map(String::valueOf).orElse("MAX");
        final String cityName = toConvert.getCity().map(City::getCityName).orElse(empty);

        return MessageFormat.format(template, minPrice, maxPrice, cityName, empty);
    }

    @Override
    public Class<?> stringifiedType() {
        return SubscriptionConfig.class;
    }
}
