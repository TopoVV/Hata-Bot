package com.topov.estatesearcher.telegram.state.subscription.steps;

import com.topov.estatesearcher.telegram.state.subscription.Subscription;
import lombok.Builder;
import lombok.Setter;

import java.util.Optional;


public interface SubscriptionUpdator {
    Subscription update(Subscription old);
}
