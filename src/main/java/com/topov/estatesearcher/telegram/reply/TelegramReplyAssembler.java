package com.topov.estatesearcher.telegram.reply;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramReplyAssembler {
    SendMessage assembleReply(UpdateResult updateResult, Keyboard keyboard, Hint hint, long chatId);
}
