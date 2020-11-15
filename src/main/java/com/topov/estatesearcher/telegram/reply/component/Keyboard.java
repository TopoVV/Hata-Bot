package com.topov.estatesearcher.telegram.reply.component;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

@Getter
public class Keyboard {
    private final ReplyKeyboardMarkup keyboardMarkup;

    public Keyboard(List<KeyboardRow> buttons) {
        this.keyboardMarkup = new ReplyKeyboardMarkup(buttons, true, false, false);
    }

    public Keyboard() {
        this.keyboardMarkup = new ReplyKeyboardMarkup(Collections.emptyList());
    }
}
