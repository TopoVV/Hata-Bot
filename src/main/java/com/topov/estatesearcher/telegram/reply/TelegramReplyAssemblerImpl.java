package com.topov.estatesearcher.telegram.reply;

import com.topov.estatesearcher.telegram.reply.component.Hint;
import com.topov.estatesearcher.telegram.reply.component.Keyboard;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class TelegramReplyAssemblerImpl implements TelegramReplyAssembler {
    @Override
    public SendMessage assembleReply(UpdateResult updateResult, Keyboard keyboard, Hint hint, long chatId) {
        final String text = String.format("%s\n%s", updateResult.getMessage(), hint.getHintMessage());
        final SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.setReplyMarkup(keyboard.createKeyboardMarkup());
        return message;

    }
}
