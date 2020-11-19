package com.topov.estatesearcher.telegram.state.subscription.handler;

import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;

public interface SubscriptionHandler {
    UpdateResult handleSubscriptionStep(Update update);
    List<KeyboardButton> getKeyboardButtons(Update update);
}
