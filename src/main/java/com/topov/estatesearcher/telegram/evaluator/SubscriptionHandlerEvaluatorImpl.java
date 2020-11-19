package com.topov.estatesearcher.telegram.evaluator;

import com.topov.estatesearcher.telegram.state.subscription.handler.SubscriptionHandlerName;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriptionHandlerEvaluatorImpl implements SubscriptionHandlerEvaluator {
    private final Map<Long, SubscriptionHandlerName> userSubscriptionHandler = new HashMap<>();

    @Override
    public void setSubscriptionStepForUser(long chatId, SubscriptionHandlerName subscriptionUpdateHandlerNames) {
        this.userSubscriptionHandler.put(chatId, subscriptionUpdateHandlerNames);
    }

    @Override
    public Optional<SubscriptionHandlerName> getCurrentSubscriptionHandlerForUser(long chatId) {
        return Optional.ofNullable(userSubscriptionHandler.get(chatId));
    }
}
