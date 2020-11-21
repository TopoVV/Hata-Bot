package com.topov.estatesearcher.telegram.state.initial;

import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TelegramBotStateAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> states = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> aClass = bean.getClass();
        if (aClass.isAnnotationPresent(TelegramBotState.class)) {
            this.states.put(beanName, aClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.states.containsKey(beanName)) {
            final Class<?> aClass = this.states.get(beanName);
            final Map<String, CommandAction> actions = new HashMap<>();

            for (Method m : aClass.getMethods()) {
                if (m.isAnnotationPresent(CommandHandler.class)) {
                    CommandHandler annotation = m.getAnnotation(CommandHandler.class);
                    actions.put(annotation.forCommand(), new CommandAction(bean, m));
                }
            }

            ReflectionUtils.doWithFields(aClass, field -> {
                if (field.getName().equals("actions")) {
                    field.setAccessible(true);
                    field.set(bean, actions);
                }
            });
        }
        return bean;
    }
}
