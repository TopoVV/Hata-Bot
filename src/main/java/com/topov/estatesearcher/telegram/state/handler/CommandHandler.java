package com.topov.estatesearcher.telegram.state.handler;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.state.BotState;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Spring MVC - like wrapper for all command handling methods. Created by
 * {@link com.topov.estatesearcher.postprocessor.CommandMappingAnnotationBeanPostProcessor} for each method annotated
 * with {@link com.topov.estatesearcher.telegram.state.annotation.CommandMapping} and stored inside the map for each
 * {@link BotState} implementation.
 */
@Log4j2
public class CommandHandler {
    private final Object bean;
    private final Method method;

    @Getter
    private final String commandPath;

    public CommandHandler(Object bean, Method method, String commandPath) {
        this.bean = bean;
        this.method = method;
        this.commandPath = commandPath;
    }

    public CommandResult act(TelegramCommand command, UserContext context)
    {
        try {
            return (CommandResult) this.method.invoke(this.bean, command, context);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error handler method invocation", e);
            throw new RuntimeException("Cannot execute", e);
        }
    }
}
