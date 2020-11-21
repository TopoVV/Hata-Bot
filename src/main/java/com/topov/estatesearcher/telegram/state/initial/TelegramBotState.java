package com.topov.estatesearcher.telegram.state.initial;

import org.springframework.stereotype.Component;

import javax.swing.text.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TelegramBotState {
    AcceptedCommand[] commands();
}
