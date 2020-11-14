package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.UpdateResult;
import lombok.Getter;

@Getter
public class SubscriptionUpdateResult extends UpdateResult {
    public SubscriptionUpdateResult(String message) {
        super(message);
    }
}
