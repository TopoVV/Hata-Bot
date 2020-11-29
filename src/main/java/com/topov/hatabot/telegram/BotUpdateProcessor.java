package com.topov.hatabot.telegram;

import com.topov.hatabot.telegram.request.UpdateWrapper;
import com.topov.hatabot.telegram.response.BotResponse;
import com.topov.hatabot.telegram.result.EntranceMessage;

import java.util.Optional;

public interface BotUpdateProcessor {
    Optional<BotResponse> processUpdate(UpdateWrapper update);
    Optional<EntranceMessage> getEntranceMessage(UpdateWrapper updateWrapper);
}
