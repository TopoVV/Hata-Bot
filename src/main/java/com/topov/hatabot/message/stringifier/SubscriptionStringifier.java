package com.topov.hatabot.message.stringifier;

import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.model.City;
import com.topov.hatabot.model.Subscription;
import com.topov.hatabot.telegram.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class SubscriptionStringifier implements ContentStringifier<Subscription> {
    private final MessageSourceAdapter messageSource;

    @Autowired
    public SubscriptionStringifier(MessageSourceAdapter messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String convert(UserContext context, Subscription toConvert) {
        final String template = messageSource.getMessage("subscription.template", context);
        final String empty = messageSource.getMessage("subscription.config.props.empty", context);
        final String anyCity = messageSource.getMessage("subscription.city.any", context);

        final String id = toConvert.getSubscriptionId().toString();
        final String minPrice = toConvert.getMinPrice().toString();
        final Integer maxPriceValue = toConvert.getMaxPrice();
        final String maxPrice = maxPriceValue.equals(Integer.MAX_VALUE) ? "MAX" : maxPriceValue.toString();
        final String cityName = toConvert.getCity().map(City::getCityName).orElse(anyCity);

        return MessageFormat.format(template, id, minPrice, maxPrice, cityName, empty);
    }

    @Override
    public Class<?> stringifiedType() {
        return Subscription.class;
    }
}
