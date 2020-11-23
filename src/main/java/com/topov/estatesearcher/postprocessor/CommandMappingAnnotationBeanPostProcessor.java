package com.topov.estatesearcher.postprocessor;

import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.handler.CommandHandler;
import com.topov.estatesearcher.telegram.state.handler.CommandInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        final Set<CommandInfo> acceptedCommands = Stream.of(stateDefinition.commands())
            .map(command -> new CommandInfo(command.commandName(), command.description()))
            .collect(Collectors.toSet());

        try {
            final Method injectorMethod = getInjectorMethod(aClass);
            for (Method method : aClass.getMethods()) {
                if (method.isAnnotationPresent(CommandMapping.class)) {
                    CommandMapping annotation = method.getAnnotation(CommandMapping.class);

                    final Optional<CommandInfo> found = acceptedCommands.stream().
                        filter(info -> info.getCommandName().equals(annotation.forCommand()))
                        .findFirst();

                    if (found.isPresent()) {
                        final CommandInfo commandInfo = found.get();
                        injectorMethod.invoke(bean, new CommandHandler(bean, method, commandInfo.getCommandName()), commandInfo);
                    }
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
