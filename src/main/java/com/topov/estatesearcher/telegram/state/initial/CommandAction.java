package com.topov.estatesearcher.telegram.state.initial;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public class CommandAction {
    private final Object bean;
    private final Method method;

    public CommandAction(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public UpdateResult act(Update update)
    {
        try {
            return (UpdateResult) this.method.invoke(this.bean, update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot execute");
        }

    }
}
