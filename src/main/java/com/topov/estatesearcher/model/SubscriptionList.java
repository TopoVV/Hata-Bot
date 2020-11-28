package com.topov.estatesearcher.model;

import com.topov.estatesearcher.telegram.context.UserContext;
import com.topov.estatesearcher.utils.MessageHelper;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionList {
    private final List<Subscription> subscriptions;

    public SubscriptionList(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String toString(UserContext context) {
        if (this.subscriptions.isEmpty()) { return MessageHelper.getMessage("reply.my.empty", context); }
        return this.subscriptions.stream()
            .map(subscription -> MessageHelper.subscriptionToMessage(subscription, context))
            .collect(Collectors.joining("\n----------\n"));
    }
}
