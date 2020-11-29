package com.topov.hatabot.telegram.keyboard;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Keyboard {
    private final List<KeyboardRow> keyboardRows = new ArrayList<>();

    public ReplyKeyboardMarkup createKeyboardMarkup() {
        return new ReplyKeyboardMarkup(keyboardRows, true, false, false);
    }

    public void addRow(KeyboardRow row) {
        this.keyboardRows.add(row);
    }
}
