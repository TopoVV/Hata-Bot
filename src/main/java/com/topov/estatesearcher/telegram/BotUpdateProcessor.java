package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.keyboard.Keyboard;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.response.BotResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface BotUpdateProcessor {
    Optional<BotResponse> processUpdate(UpdateWrapper update);
    SendMessage getMessage(UpdateWrapper updateWrapper);
    Keyboard getKeyboard(Update update);

}
