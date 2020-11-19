package com.topov.estatesearcher.telegram.executor;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.Set;

@Log4j2
@Service
public class SaveCommandExecutor implements CommandExecutor {
    @Override
    public void execute(Update update) {
        log.info("Executing /cancel command!!!");
    }

    @Override
    public Set<String> executes() {
        return Collections.singleton("/save");
    }
}
