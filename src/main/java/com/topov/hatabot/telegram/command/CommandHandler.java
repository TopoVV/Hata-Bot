package com.topov.hatabot.telegram.command;

import com.topov.hatabot.postprocessor.CommandMappingAnnotationBPP;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.state.BotState;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Spring MVC - like wrapper for all command handling methods. Created by
 * {@link CommandMappingAnnotationBPP} for each method annotated
 * with {@link com.topov.hatabot.telegram.state.annotation.CommandMapping} and stored inside the map for each
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

    public CommandResult handle(TelegramCommand command, UserContext context)
    {
        try {
            final Object result =  this.method.invoke(bean, command, context);
            return (result == null) ? new CommandResult() : (CommandResult) result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error handler method invocation", e);
            throw new RuntimeException("Cannot execute", e);
        }
    }
}
