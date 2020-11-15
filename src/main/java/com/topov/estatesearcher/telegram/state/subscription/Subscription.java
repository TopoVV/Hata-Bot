package com.topov.estatesearcher.telegram.state.subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    private Integer price;

    public Subscription(Subscription subscription) {
        this.price = subscription.getPrice();
    }
}
