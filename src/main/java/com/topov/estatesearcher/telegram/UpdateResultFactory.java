package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;

public interface UpdateResultFactory {
    UpdateResult createUpdateResult(String messageCode);
    UpdateResult createUpdateResult(String messageCode, Object[] ... args);
    UpdateResult createUpdateResult(String messageCode, String commandsCode);
}
