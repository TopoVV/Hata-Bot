package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.state.subscription.steps.AbstractSubscriptionStep;
import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
public class SubscriptionStepProviderImpl implements SubscriptionStepProvider {
    private final Map<SubscriptionStep.StepName, AbstractSubscriptionStep> subscriptionSteps;
    private final Map<Long, SubscriptionStep.StepName> userSubscriptionSteps = new HashMap<>();

    @Autowired
    public SubscriptionStepProviderImpl(List<AbstractSubscriptionStep> subscriptionSteps) {
        this.subscriptionSteps = subscriptionSteps.stream()
            .collect(toMap(
                AbstractSubscriptionStep::getStepName,
                Function.identity()
            )
        );
    }

    @Override
    public SubscriptionStep getSubscriptionStep(SubscriptionStep.StepName stepName) {
        return subscriptionSteps.get(stepName);
    }

    @Override
    public Optional<SubscriptionStep.StepName> getCurrentStepName(long chatId) {
        return Optional.ofNullable(userSubscriptionSteps.get(chatId));
    }

    @Override
    public void setSubscriptionStepForUser(long chatId, SubscriptionStep.StepName stepName) {
        this.userSubscriptionSteps.put(chatId, stepName);
    }

    @Override
    public void resetSubscriptionStepForUser(long chatId) {
        this.userSubscriptionSteps.remove(chatId);
    }

}