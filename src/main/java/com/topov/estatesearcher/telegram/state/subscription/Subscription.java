package com.topov.estatesearcher.telegram.state.subscription;

import com.topov.estatesearcher.telegram.state.subscription.steps.SubscriptionUpdator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Integer price;

    public Subscription(Subscription subscription) {
        this.price = subscription.getPrice();
    }
}
