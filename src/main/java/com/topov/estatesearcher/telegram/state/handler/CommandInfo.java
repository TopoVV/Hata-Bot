package com.topov.estatesearcher.telegram.state.handler;

import lombok.Getter;

import java.util.Objects;

@Getter
public class CommandInfo {
    private final String commandName;
    private final String description;

    public CommandInfo(String commandName, String description) {
        this.commandName = commandName;
        this.description = description;
    }

    public CommandInfo(String commandName) {
        this.commandName = commandName;
        this.description = "";
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
        return String.format("%s - %s", this.commandName, this.description);
    }
}
