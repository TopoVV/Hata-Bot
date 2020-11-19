package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.executor.CommandExecutor;

import java.util.Optional;

public interface CommandExecutorProvider {
    Optional<CommandExecutor> getExecutor(String command);
}
