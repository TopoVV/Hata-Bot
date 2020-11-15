package com.topov.estatesearcher.telegram.reply;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class TelegramReplyAssemblerImpl implements TelegramReplyAssembler {
    @Override
    public SendMessage assembleReply(UpdateResult updateResult, Keyboard keyboard, Hint hint, long chatId) {
        final String text = String.format("%s\n%s", updateResult.getMessage(), hint.getMessage());
        final SendMessage telegramMessage = new SendMessage(String.valueOf(chatId), text);
        telegramMessage.setReplyMarkup(keyboard.getKeyboardMarkup());

        return telegramMessage;
    }
}
