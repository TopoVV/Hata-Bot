package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public class CommandHandler {
    private final Object bean;
    private final Method method;

    @Getter
    private final String command;

    public CommandHandler(Object bean, Method method, String command) {
        this.bean = bean;
        this.method = method;
        this.command = command;
    }

    public UpdateResult act(Update update)
    {
        try {
            return (UpdateResult) this.method.invoke(this.bean, update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error handler method invocation", e);
            throw new RuntimeException("Cannot execute", e);
        }
    }
}
