package com.topov.hatabot.message.stringifier;

import com.topov.hatabot.model.City;
import com.topov.hatabot.telegram.context.UserContext;
import org.springframework.stereotype.Component;

@Component
public class CityStringifier implements ContentStringifier<City> {
    @Override
    public String convert(UserContext context, City toConvert) {
        return toConvert.toString();
    }

    @Override
    public Class<?> stringifiedType() {
        return City.class;
    }
}
