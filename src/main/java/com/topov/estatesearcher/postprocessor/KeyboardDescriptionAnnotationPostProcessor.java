package com.topov.estatesearcher.postprocessor;

import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class KeyboardDescriptionAnnotationPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> statesWithKeyboard = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> aClass = bean.getClass();
        if (aClass.isAnnotationPresent(KeyboardDescription.class)) {
            this.statesWithKeyboard.put(beanName, aClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!this.statesWithKeyboard.containsKey(beanName)) {
            return bean;
        }

        try {
            final Keyboard keyboard = new Keyboard();
            final Class<?> aClass = this.statesWithKeyboard.get(beanName);
            final KeyboardDescription annotation = aClass.getAnnotation(KeyboardDescription.class);

            Stream.of(annotation.rows()).forEach(keyboardRow -> {
                final KeyboardRow row = new KeyboardRow();
                Stream.of(keyboardRow.buttons()).forEach(row::add);
                keyboard.addRow(row);
            });

            final Method setKeyboard = aClass.getMethod("setKeyboard", Keyboard.class);
            setKeyboard.invoke(bean, keyboard);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }
}
