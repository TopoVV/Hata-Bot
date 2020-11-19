package com.topov.estatesearcher.telegram.state.subscription.handler;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.service.SubscriptionService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.evaluator.SubscriptionHandlerEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.update.CityUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Log4j2
@Service
public class CitySubscriptionHandler extends AbstractSubscriptionHandler {
    private final CityService cityService;

    @Autowired
    public CitySubscriptionHandler(BotStateEvaluator stateEvaluator,
                                   SubscriptionCache subscriptionCache,
                                   SubscriptionService subscriptionService,
                                   SubscriptionHandlerEvaluator stepEvaluator,
                                   UpdateResultFactory updateResultFactory,
                                   CityService cityService) {
        super(SubscriptionHandlerName.CITY, stateEvaluator, stepEvaluator, subscriptionCache, subscriptionService, updateResultFactory);
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling city subscription step");
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        switch (text) {
            case "/cancel": return this.handleCancelCommand(chatId);
            case "/save": return this.handleSaveCommand(chatId);
            default: return this.handleCityUpdate(update);
        }
    }

    private UpdateResult handleCityUpdate(Update update) {
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        try {
            final int cityId = Integer.parseInt(text);
            final Optional<City> city = this.cityService.getCity(cityId);
            if (city.isPresent()) {
                this.subscriptionCache.modifySubscription(chatId, new CityUpdate(city.get()));
                return this.updateResultFactory.createUpdateResult("replies.subscription.update.success");
            } else {
                return this.updateResultFactory.createUpdateResult("replies.subscription.update.city.fail.notFound");
            }
        } catch (NumberFormatException e) {
            log.error("Invalid city: {}", text);
            return this.updateResultFactory.createUpdateResult("replies.subscription.update.city.fail.invalidInput", new Object[] { text });
        }
    }
}
