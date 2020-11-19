package com.topov.estatesearcher.telegram.state.subscription;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.provider.CommandExecutorProvider;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.subscription.update.CityUpdate;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
public class CitySubscriptionBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;
    private final CommandExecutorProvider executorProvider;
    private final CityService cityService;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache,
                                    CommandExecutorProvider executorProvider,
                                    CityService cityService) {
        super(BotStateName.SUBSCRIPTION_CITY);
        this.subscriptionCache = subscriptionCache;
        this.executorProvider = executorProvider;
        this.cityService = cityService;
        this.supportedCommands = Collections.singletonList("/subscriptions");
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling city subscription step");
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        if (isSupportedCommand(text)) {
            this.executorProvider.getExecutor(text).ifPresent(executor -> executor.execute(update));
        }

        return new UpdateResult("SUBSCRIPTION CITY BOT STATE");
    }

//    private UpdateResult handleCityUpdate(Update update) {
//        final String text = update.getMessage().getText();
//        final Long chatId = update.getMessage().getChatId();
//
//        try {
//            final int cityId = Integer.parseInt(text);
//            final Optional<City> city = this.cityService.getCity(cityId);
//            if (city.isPresent()) {
//                this.subscriptionCache.modifySubscription(chatId, new CityUpdate(city.get()));
//                return this.updateResultFactory.createUpdateResult("replies.subscription.update.success");
//            } else {
//                return this.updateResultFactory.createUpdateResult("replies.subscription.update.city.fail.notFound");
//            }
//        } catch (NumberFormatException e) {
//            log.error("Invalid city: {}", text);
//            return this.updateResultFactory.createUpdateResult("replies.subscription.update.city.fail.invalidInput", new Object[] { text });
//        }
//    }
}
