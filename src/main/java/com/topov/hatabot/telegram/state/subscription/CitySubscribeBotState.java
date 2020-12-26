package com.topov.hatabot.telegram.state.subscription;

import com.topov.hatabot.ListItemContent;
import com.topov.hatabot.model.City;
import com.topov.hatabot.service.CityService;
import com.topov.hatabot.telegram.context.SubscriptionConfig;
import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.telegram.keyboard.KeyboardDescription;
import com.topov.hatabot.telegram.keyboard.KeyboardRow;
import com.topov.hatabot.telegram.request.TelegramCommand;
import com.topov.hatabot.telegram.request.TelegramUpdate;
import com.topov.hatabot.telegram.result.CommandResult;
import com.topov.hatabot.telegram.result.UpdateResult;
import com.topov.hatabot.telegram.state.BotStateName;
import com.topov.hatabot.telegram.state.annotation.AcceptedCommand;
import com.topov.hatabot.telegram.state.annotation.CommandMapping;
import com.topov.hatabot.telegram.state.annotation.TelegramBotState;
import com.topov.hatabot.utils.MessageHelper;
import com.topov.hatabot.utils.StateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@TelegramBotState(commands = {
    @AcceptedCommand(commandName = "/back"),
    @AcceptedCommand(commandName = "/cities"),
    @AcceptedCommand(commandName = "/current")
})
@KeyboardDescription(rows = {
    @KeyboardRow(buttons = { "/back" }),
    @KeyboardRow(buttons = { "/cities" }),
    @KeyboardRow(buttons = { "/current" }),
})
public class CitySubscribeBotState extends AbstractSubscribeBotState {
    private final CityService cityService;

    @Autowired
    public CitySubscribeBotState(CityService cityService) {
        super(StateUtils.CITY_PROPS);
        this.cityService = cityService;
    }

    @Override
    public UpdateResult handleUpdate(TelegramUpdate update, UserContext context) {
        log.debug("Handling city update");
        final String text = update.getText();

        try {
            final Optional<City> optionalCity = findCity(text);
            if (!optionalCity.isPresent()) {
                final String message = MessageHelper.getMessage("reply.city.not.found", context, text);
                return UpdateResult.withMessage(message);
            }

            final City city = optionalCity.get();
            final SubscriptionConfig subscriptionConfig = context.getSubscriptionConfig();
            subscriptionConfig.setCity(city);
            context.setSubscriptionConfig(new SubscriptionConfig(subscriptionConfig));
            context.setCurrentStateName(BotStateName.SUBSCRIBE);

            final String message = MessageHelper.getMessage("reply.city", context, city.getCityName());
            return UpdateResult.withMessage(message);
        } catch (NumberFormatException e) {
            log.error("Invalid id {}", text, e);
            final String message = MessageHelper.getMessage("reply.city.invalid.input", context, text);
            return UpdateResult.withMessage(message);
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

    @CommandMapping(forCommand = "/back")
    public void onBack(TelegramCommand command, UserContext context) {
        context.setCurrentStateName(BotStateName.SUBSCRIBE);
    }

    @CommandMapping(forCommand = "/cities")
    public CommandResult onCities(TelegramCommand command, UserContext context) {
        final List<City> cities = this.cityService.getCities();
        return new CommandResult("reply.cities", new ListItemContent<>(cities));
    }

    @CommandMapping(forCommand = "/current")
    public CommandResult onCurrent(TelegramCommand command, UserContext context) {
        final DefaultCurrentExecutor executor = new DefaultCurrentExecutor();
        return executor.execute(command, context);
    }
}
