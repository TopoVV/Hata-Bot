package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.UserContext;
import com.topov.estatesearcher.telegram.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.TelegramUpdate;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.subscription.update.CityUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Optional;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel")
})
public class CitySubscriptionBotState extends AbstractSubscriptionBotState {
    private final CityService cityService;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache, CityService cityService, MessageSource messageSource) {
        super(BotStateName.SUBSCRIPTION_CITY, messageSource, subscriptionCache);
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        log.debug("Handling city subscription step");
        final String text = update.getText();
        final Long chatId = update.getChatId();

        try {
            final int cityId = Integer.parseInt(text);
            final Optional<City> city = this.cityService.getCity(cityId);

            if (city.isPresent()) {
                this.subscriptionCache.modifySubscription(chatId, new CityUpdate(city.get()));
                return new UpdateResult(chatId, "Done");
            }

            return new UpdateResult(chatId, "City not found");
        } catch (NumberFormatException e) {
            log.error("Invalid city: {}", text);
            return new UpdateResult(chatId, "Invalid city id");
        }
    }
}
