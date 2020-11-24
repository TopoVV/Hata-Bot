package com.topov.estatesearcher.telegram.state;

public class StateUtils {
    public static final StateProperties MAIN_PROPS =
        new StateProperties(BotStateName.MAIN, "main.header", "main.commands");

    public static final StateProperties MANAGEMENT_PROPS =
        new StateProperties(BotStateName.MANAGEMENT, "management.header", "management.commands");

    public static final StateProperties UNSUBSCRIBE_PROPS =
        new StateProperties(BotStateName.UNSUBSCRIBE, "unsubscribe.header", "unsubscribe.commands");

    public static final StateProperties SUBSCRIBE_PROPS =
        new StateProperties(BotStateName.SUBSCRIBE, "subscribe.header", "subscribe.commands");

    public static final StateProperties CITY_PROPS =
        new StateProperties(BotStateName.SUBSCRIPTION_CITY, "city.header", "city.commands");

    public static final StateProperties MAX_PRICE_PROPS =
        new StateProperties(BotStateName.SUBSCRIPTION_MAX_PRICE, "maxPrice.header", "maxPrice.commands");

    public static final StateProperties MIN_PRICE_PROPS =
        new StateProperties(BotStateName.SUBSCRIPTION_MIN_PRICE, "minPrice.header", "minPrice.commands");

    public static final StateProperties LANGUAGE_PROPS =
        new StateProperties(BotStateName.CHOOSE_LANGUAGE, "language.header", "language.commands");

    public static final StateProperties DONATE_PROPS =
        new StateProperties(BotStateName.DONATE, "donate.header", "donate.commands");

    public static final StateProperties ANONYMOUS_PROPS =
        new StateProperties(BotStateName.ANONYMOUS, "", "");
}
