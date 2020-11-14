package com.topov.estatesearcher.telegram;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Getter
public class Keyboard {
    private final ReplyKeyboardMarkup keyboardMarkup;

    public Keyboard(List<KeyboardRow> buttons) {
        this.keyboardMarkup = new ReplyKeyboardMarkup(buttons);
    }
}
