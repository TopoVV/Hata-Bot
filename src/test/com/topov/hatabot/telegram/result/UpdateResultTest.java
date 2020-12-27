package com.topov.hatabot.telegram.result;

import com.topov.hatabot.message.source.MessageSourceAdapter;
import com.topov.hatabot.message.source.MessageSourceAdapterImpl;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.state.BotStateName;
import dao.DaoTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UpdateResultTest.UpdateResultTestContextConfig.class })
public class UpdateResultTest {
    private final MessageSourceAdapter messageSource;

    @Autowired
    public UpdateResultTest(MessageSourceAdapter messageSource) {
        this.messageSource = messageSource;
    }


    @Test
    public void stringifyRu() {
        final UpdateResult updateResult = new UpdateResult("reply.min.price", "1000");
        final UserContext userContext = new UserContext("1111", BotStateName.SUBSCRIBE);
        userContext.setLocale(new Locale("ru"));
        final String stringify = updateResult.stringify(messageSource, userContext);
        assertEquals("Теперь ты отслеживаешь недвижимость, которая дороже 1000 ГРН. Можешь сохранить(/save) или задать другие параметры(/maxPrice, /city).", stringify);
    }

    @Test
    public void stringifyEn() {
        final UpdateResult updateResult = new UpdateResult("reply.min.price", "1000");
        final UserContext userContext = new UserContext("1111", BotStateName.SUBSCRIPTION_MIN_PRICE);
        userContext.setLocale(new Locale("en"));
        final String stringify = updateResult.stringify(messageSource, userContext);
        assertEquals("Now you are tracking estate more expensive than 1000 UAH only. You can /save or specify other parameters(/maxPrice, /city).", stringify);
    }

    @Configuration
    public static class UpdateResultTestContextConfig {
        @Bean
        MessageSource messageSource() {
            ResourceBundleMessageSource source = new ResourceBundleMessageSource();
            source.setBasenames("messages/messages");
            source.setCacheSeconds(3600);
            source.setDefaultEncoding("UTF-8");
            source.setUseCodeAsDefaultMessage(true);
            return source;
        }

        @Bean
        MessageSourceAdapter messageSourceAdapter() {
            return new MessageSourceAdapterImpl(messageSource());
        }
    }
}
