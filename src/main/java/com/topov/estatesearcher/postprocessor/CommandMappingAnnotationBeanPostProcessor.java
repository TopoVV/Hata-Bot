package com.topov.estatesearcher.postprocessor;

import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.handler.CommandHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final TelegramBotState stateDefinition = aClass.getAnnotation(TelegramBotState.class);
        final Set<String> acceptedCommands = Stream.of(stateDefinition.commands())
            .map(AcceptedCommand::commandName)
            .collect(Collectors.toSet());

        try {
            final Method injectorMethod = getInjectorMethod(aClass);
            scan(aClass, bean, injectorMethod, acceptedCommands);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private void scan(Class<?> aClass, Object bean, Method injectorMethod, Set<String> acceptedCommands) throws InvocationTargetException, IllegalAccessException {
        if (aClass == null || aClass == Object.class) {
            return;
        }
        scan(aClass.getSuperclass(), bean, injectorMethod, acceptedCommands);
        for (Method method : aClass.getMethods()) {
            if (method.isAnnotationPresent(CommandMapping.class)) {
                CommandMapping annotation = method.getAnnotation(CommandMapping.class);
                final String commandPath = annotation.forCommand();
                if (acceptedCommands.contains(commandPath)) {
                    injectorMethod.invoke(bean, new CommandHandler(bean, method, commandPath));
                }
            }
        }
    }

    private Method getInjectorMethod(Class<?> aClass) throws NoSuchMethodException {
        return aClass.getMethod("addCommandHandler", CommandHandler.class);
    }
}
