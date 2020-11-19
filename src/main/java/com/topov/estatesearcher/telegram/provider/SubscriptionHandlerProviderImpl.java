package com.topov.estatesearcher.telegram.provider;

import com.topov.estatesearcher.telegram.state.subscription.handler.AbstractSubscriptionHandler;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandler;
import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandlerName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * The same as {@link BotStateProvider} this class stores Singleton implementation for each {@link SubscriptionHandler}
 * which are requested by {@link com.topov.estatesearcher.telegram.state.subscription.SubscriptionBotState}.
 */
@Service
public class SubscriptionHandlerProviderImpl implements SubscriptionHandlerProvider {
    private final Map<SubscriptionHandlerName, AbstractSubscriptionHandler> subscriptionHandlers;

    @Autowired
    public SubscriptionHandlerProviderImpl(List<AbstractSubscriptionHandler> subscriptionHandlers) {
        this.subscriptionHandlers = subscriptionHandlers.stream()
            .collect(toMap(
                AbstractSubscriptionHandler::getSubscriptionHandlerName,
                Function.identity()
            )
        );
    }

    @Override
    public SubscriptionHandler getSubscriptionStep(SubscriptionHandlerName subscriptionUpdateHandlerNames) {
        return subscriptionHandlers.get(subscriptionUpdateHandlerNames);
    }
}
