package com.topov.estatesearcher.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface BotUpdateProcessor {
    Optional<BotResponse> processUpdate(UpdateWrapper update);
    Keyboard getKeyboard(Update update);

}
