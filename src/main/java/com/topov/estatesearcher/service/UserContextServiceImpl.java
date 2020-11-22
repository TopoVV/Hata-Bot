package com.topov.estatesearcher.service;

import com.topov.estatesearcher.telegram.TelegramCommand;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.state.BotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.CommandResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class UserContextServiceImpl implements UserContextService {
    private final Map<Long, UserContext> userContexts = new HashMap<>();

    @Override
    public void setContext(UserContext userContext) {
        this.userContexts.put(userContext.getChatId(), userContext);
    }

    @Override
    public UserContext getContextForUser(Long chatId) {

        if (this.userContexts.containsKey(chatId)) {
            return new UserContext(this.userContexts.get(chatId));
        } else {
            return new UserContext(chatId, BotStateName.INITIAL) {
                @Override
                public CommandResult executeCommand(TelegramCommand command, Map<BotStateName, BotState> states) {
                    if (command.isStart()) {
                        final CommandResult commandResult = states.get(BotStateName.INITIAL).executeCommand(command);
                        commandResult.changedState().ifPresent(this::changeState);
                        return commandResult;
                    }
                    throw new RuntimeException("Temporal exception");
                }

                @Override
                public String getCurrentStateEntranceMessage(Map<BotStateName, BotState> states) {
                    return "";
                }
            };
        }
    }

    @Override
    public void createContext(Long chatId) {
        this.userContexts.put(chatId, new UserContext(chatId, BotStateName.INITIAL));
    }
}
