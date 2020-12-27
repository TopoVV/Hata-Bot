package com.topov.hatabot.postprocessor;

import com.topov.hatabot.telegram.command.CommandHandler;
import com.topov.hatabot.telegram.command.CommandInfo;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandMappingAnnotationBPP implements BeanPostProcessor {
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
        final TelegramBotState stateDefinition = aClass.getAnnotation(TelegramBotState.class);

        try {
            final Method injectorMethod = getInjectorMethod(aClass);
            for (Method method : aClass.getMethods()) {
                if (method.isAnnotationPresent(CommandMapping.class)) {
                    CommandMapping mapping = method.getAnnotation(CommandMapping.class);
                    final String commandName = mapping.forCommand();
                    final CommandInfo commandInfo = new CommandInfo(commandName);
                    injectorMethod.invoke(bean, new CommandHandler(bean, method, commandName), commandInfo);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private Method getInjectorMethod(Class<?> aClass) throws NoSuchMethodException {
        return aClass.getMethod("addCommandHandler", CommandHandler.class, CommandInfo.class);
    }
}