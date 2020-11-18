package com.topov.estatesearcher.telegram.state.subscription.step;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.UpdateResultFactory;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.subscription.update.CityUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class CitySubscriptionStep extends AbstractSubscriptionStep {
    private final CityService cityService;
    private final SubscriptionCache subscriptionCache;
    private final UpdateResultFactory updateResultFactory;

    @Autowired
    public CitySubscriptionStep(CityService cityService, SubscriptionCache subscriptionCache, UpdateResultFactory updateResultFactory) {
        super(StepName.CITY);
        this.cityService = cityService;
        this.subscriptionCache = subscriptionCache;
        this.updateResultFactory = updateResultFactory;
    }

    @Override
    public UpdateResult handleSubscriptionStep(Update update) {
        log.debug("Handling city subscription step");
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
