package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.executor.CommandExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class CommandExecutorProviderImpl implements CommandExecutorProvider {
    private final Map<String, CommandExecutor> commandExecutors;

    @Autowired
    public CommandExecutorProviderImpl(List<CommandExecutor> commandExecutors) {
        this.commandExecutors = new HashMap<>();

        for (CommandExecutor ex : commandExecutors) {
            for (String command : ex.executes()) {
                this.commandExecutors.put(command, ex);
            }
        }
    }
    @Override
    public Optional<CommandExecutor> getExecutor(String command) {
        return Optional.of(commandExecutors.get(command));
    }
}
