package com.topov.estatesearcher.telegram.executor;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Set;

public interface CommandExecutor {
    void execute(Update update);
    Set<String> executes();
}
