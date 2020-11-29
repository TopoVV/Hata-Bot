package com.topov.hatabot.model;

import com.topov.hatabot.telegram.context.UserContext;
import com.topov.hatabot.utils.MessageHelper;

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
