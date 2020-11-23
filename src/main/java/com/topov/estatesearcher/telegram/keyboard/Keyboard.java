package com.topov.estatesearcher.telegram.keyboard;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Keyboard {
    private final List<KeyboardRow> keyboardRows = new ArrayList<>();

    public void AddButtons(List<KeyboardButton> buttons) {
        buttons.forEach(this::addOneButton);
    }

    public void addOneButton(KeyboardButton button) {
        this.addButton(button);
    }

    public ReplyKeyboardMarkup createKeyboardMarkup() {
        return new ReplyKeyboardMarkup(keyboardRows, true, true, false);
    }

    private void addButton(KeyboardButton button) {
        final int lastRowInd = this.keyboardRows.size() - 1;
        if (lastRowInd < 0 || this.keyboardRows.get(lastRowInd).size() >= 2) {
            this.keyboardRows.add(new KeyboardRow());
        }

        this.keyboardRows.get(this.keyboardRows.size() - 1).add(button);
    }
}
