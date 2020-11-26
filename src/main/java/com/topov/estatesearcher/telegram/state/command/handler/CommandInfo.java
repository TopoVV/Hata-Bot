package com.topov.estatesearcher.telegram.state.command.handler;

import lombok.Getter;

import java.util.Objects;

@Getter
public class CommandInfo {
    private final String commandName;

    public CommandInfo(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandInfo that = (CommandInfo) o;
        return commandName.equals(that.commandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandName);
    }

    @Override
    public String toString() {
        return this.commandName;
    }
}
