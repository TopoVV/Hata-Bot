package com.topov.estatesearcher.telegram.state.subscription;

import com.google.common.collect.Lists;
import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.evaluator.BotStateEvaluator;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

@Log4j2
@Service
public class CitySubscriptionBotState extends AbstractBotState {
    private final SubscriptionCache subscriptionCache;
    private final CityService cityService;
    private final BotStateEvaluator stateEvaluator;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache, CityService cityService, BotStateEvaluator stateEvaluator) {
        super(BotStateName.SUBSCRIPTION_CITY);
        this.subscriptionCache = subscriptionCache;
        this.cityService = cityService;
        this.stateEvaluator = stateEvaluator;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling city subscription step");
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();



        return new UpdateResult("SUBSCRIPTION CITY BOT STATE");
    }

    @Override
    public UpdateResult executeCommand(String command, Update update) {
        final long chatId = update.getMessage().getChatId();

        switch (command) {
            case "/cancel": this.stateEvaluator.setStateForUser(chatId, BotStateName.SUBSCRIPTION); break;
            case "/main": this.stateEvaluator.setStateForUser(chatId, BotStateName.INITIAL); break;
        }

        return new UpdateResult("Command executed");
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
