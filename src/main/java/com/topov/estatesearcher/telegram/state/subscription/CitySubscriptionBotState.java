package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.cache.SubscriptionCache;
import com.topov.estatesearcher.model.City;
import com.topov.estatesearcher.model.Subscription;
import com.topov.estatesearcher.service.CityService;
import com.topov.estatesearcher.telegram.EntranceMessage;
import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.telegram.keyboard.KeyboardDescription;
import com.topov.estatesearcher.telegram.keyboard.KeyboardRow;
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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back", description = "back.description"),
    @AcceptedCommand(commandName = "/cities", description = "cities.description"),
    @AcceptedCommand(commandName = "/current", description = "current.description")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/cities" }),
    @KeyboardRow(buttons = { "/current" }),
})
public class CitySubscriptionBotState extends AbstractBotState {
    private static final String HEADER = "Specify city.";

    private final CityService cityService;
    private final SubscriptionCache subscriptionCache;

    @Autowired
    public CitySubscriptionBotState(SubscriptionCache subscriptionCache, CityService cityService) {
        super(BotStateName.SUBSCRIPTION_CITY);
        this.subscriptionCache = subscriptionCache;
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        log.debug("Handling city update");
        final String chatId = context.getChatId();
        final String text = update.getText();

        try {
            final Optional<City> optionalCity = findCity(text);
            if (optionalCity.isPresent()) {
                final City city = optionalCity.get();
                this.subscriptionCache.modifySubscription(chatId, new CityUpdate(city));
                context.changeState(BotStateName.SUBSCRIPTION);

                final String current = this.subscriptionCache.getCachedSubscription(chatId)
                    .map(Subscription::toString)
                    .orElse("Not yet created");

                final String template = "Current:%s\n\nCity saved.";
                return UpdateResult.withMessage(String.format(template, current));
            }

            return UpdateResult.withMessage(String.format("City %s not found", text));
        } catch (NumberFormatException e) {
            log.error("Invalid id {}", text, e);
            return UpdateResult.withMessage(String.format("Invalid id %s", text));
        }
    }

    private Optional<City> findCity(String text) throws NumberFormatException {
        final Pattern p = Pattern.compile("[0-9]+");
        final Matcher matcher = p.matcher(text);

        if (matcher.matches()) {
            final Integer cityId = Integer.valueOf(text);
            return this.cityService.getCity(cityId);
        } else {
            return this.cityService.getCity(text);
        }
    }

    @Override
    public Optional<EntranceMessage> getEntranceMessage(UpdateWrapper update, UserContext context) {
        final String entranceText = String.format("%s\n\nCommands:\n%s", HEADER, commandsInformationString());
        return Optional.of(new EntranceMessage(context.getChatId(), entranceText, this.getKeyboard()));
    }

    @CommandMapping(forCommand = "/cities")
    public CommandResult onCities(TelegramCommand command, UserContext context) {
        log.info("Executing /city command for user {}", context.getChatId());
        final String cities = this.cityService.getCities().stream()
            .map(City::toString)
            .collect(Collectors.joining("\n"));

        return CommandResult.withMessage(String.format("Available cities:\n%s", cities));
    }

    @CommandMapping(forCommand = "/back")
    public CommandResult onBack(TelegramCommand command, UserContext context) {
        log.info("Executing /back command for user {}", context.getChatId());
        context.changeState(BotStateName.SUBSCRIPTION);
        return CommandResult.empty();
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        log.info("Executing /cancel command for user {}", context.getChatId());

        final String current = this.subscriptionCache.getCachedSubscription(context.getChatId())
            .map(Subscription::toString)
            .orElse("Not created yet");

        return CommandResult.withMessage(String.format("Current:\n%s", current));
    }
}
