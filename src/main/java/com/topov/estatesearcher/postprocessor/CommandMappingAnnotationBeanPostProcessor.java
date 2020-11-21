package com.topov.estatesearcher.postprocessor;

import com.topov.estatesearcher.telegram.state.CommandHandler;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandMappingAnnotationBeanPostProcessor implements BeanPostProcessor {
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
        if (!this.states.containsKey(beanName)) {
            return bean;
        }

        final Class<?> aClass = this.states.get(beanName);
        try {
            final Method injectorMethod = getInjectorMethod(aClass);
            scan(aClass, bean, injectorMethod);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private void scan(Class<?> aClass, Object bean, Method injectorMethod) throws InvocationTargetException, IllegalAccessException {
        if (aClass == null || aClass == Object.class) {
            return;
        }
        scan(aClass.getSuperclass(), bean, injectorMethod);
        for (Method handlerMethod : aClass.getMethods()) {
            if (handlerMethod.isAnnotationPresent(CommandMapping.class)) {
                CommandMapping annotation = handlerMethod.getAnnotation(CommandMapping.class);
                final String command = annotation.forCommand();
                injectorMethod.invoke(bean, new CommandHandler(bean, handlerMethod, command));
            }
        }
    }

    private Method getInjectorMethod(Class<?> aClass) throws NoSuchMethodException {
        return aClass.getMethod("addCommandHandler", CommandHandler.class);
    }
}
