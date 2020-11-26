package com.topov.estatesearcher.telegram;

import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.response.BotResponse;

import java.util.Optional;

public interface BotUpdateProcessor {
    Optional<BotResponse> processUpdate(UpdateWrapper update);
    Optional<EntranceMessage> getEntranceMessage(UpdateWrapper updateWrapper);
}
