package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.request.TelegramCommand;
import com.topov.estatesearcher.telegram.request.TelegramUpdate;
import com.topov.estatesearcher.telegram.request.UpdateWrapper;
import com.topov.estatesearcher.telegram.result.CommandResult;
import com.topov.estatesearcher.telegram.result.UpdateResult;
import com.topov.estatesearcher.telegram.state.AbstractBotState;
import com.topov.estatesearcher.telegram.state.BotStateName;
import com.topov.estatesearcher.telegram.state.annotation.AcceptedCommand;
import com.topov.estatesearcher.telegram.state.annotation.CommandMapping;
import com.topov.estatesearcher.telegram.state.annotation.TelegramBotState;
import com.topov.estatesearcher.telegram.state.subscription.update.CityUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/cities"),
    @AcceptedCommand(commandName = "/current")
})
public class CitySubscriptionBotState extends AbstractBotState {
    public static final String ENTRANCE_MESSAGE = "Specify city.\n\n" +
        "Commands:\n" +
        "/cancel - go back\n" +
        "/cities - available cities\n" +
        "/current - city current subscription config";

    private final CityService cityService;
    private final SubscriptionCache subscriptionCache;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache, CityService cityService) {
        super(BotStateName.SUBSCRIPTION_CITY);
        this.subscriptionCache = subscriptionCache;
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext.ChangeStateCallback changeState) {
        log.debug("Handling city subscription step");
        final Long chatId = update.getChatId();
        final String text = update.getText();

        try {
            final City city = this.cityService.getCity(text);
            this.subscriptionCache.modifySubscription(chatId, new CityUpdate(city));
            changeState.accept(BotStateName.SUBSCRIPTION);

            final String current = this.subscriptionCache.getCachedSubscription(chatId)
                .map(Subscription::toString)
                .orElse("Not yet created");

            final String template = "Current:\n%s\n\nCity saved.";
            final String message = String.format(template, current);
            return new UpdateResult(message);
        } catch (EmptyResultDataAccessException e) {
            log.error("City {} not found", text, e);
            return new UpdateResult(String.format("City %s not found", text));
        }
    }

    @Override
    public String getEntranceMessage(UpdateWrapper update) {
        return ENTRANCE_MESSAGE;
    }

    @CommandMapping(forCommand = "/cities")
    public CommandResult onCities(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /city command");
        final String cities = this.cityService.getCities().stream()
            .map(City::getCityName)
            .collect(Collectors.joining("\n"));

        return CommandResult.withMessage(String.format("Available cities:\n%s", cities));
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /back command");
        changeState.accept(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext.ChangeStateCallback changeState) {
        log.info("Executing /cancel command");

        final String current = this.subscriptionCache.getCachedSubscription(command.getChatId())
            .map(Subscription::toString)
            .orElse("Not created yet");

        return CommandResult.withMessage(String.format("Current:\n%s", current));
    }
}
