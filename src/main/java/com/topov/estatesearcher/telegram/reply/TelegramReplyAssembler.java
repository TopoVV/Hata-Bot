package com.topov.estatesearcher.telegram.reply;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramReplyAssembler {
    SendMessage assembleReply(UpdateResult updateResult, Keyboard keyboard, Hint hint, long chatId);
}