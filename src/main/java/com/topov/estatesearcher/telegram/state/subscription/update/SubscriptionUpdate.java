package com.topov.estatesearcher.telegram.state.subscription.update;

import com.topov.estatesearcher.model.Subscription;


public interface SubscriptionUpdate {
    Subscription update(Subscription old);
}
