package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.reply.component.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractSubscriptionBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/help"),
    @AcceptedCommand(commandName = "/cancel")
})
public class CitySubscriptionBotState extends AbstractSubscriptionBotState {
    private final CityService cityService;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache, CityService cityService) {
        super(BotStateName.SUBSCRIPTION_CITY, subscriptionCache);
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Handling city subscription step");
        final String text = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        return new UpdateResult("SUBSCRIPTION CITY BOT STATE");
    }

    @Override
    public String getEntranceMessage() {
        return "SUBSCRIPTION CITY BOT STATE";
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
