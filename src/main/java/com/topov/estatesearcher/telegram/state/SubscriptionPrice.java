package com.topov.estatesearcher.telegram.state;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Service
public class SubscriptionPrice extends AbstractSubscriptionStep {
    protected SubscriptionPrice() {
        super(1);
    }

    @Override
    public SubscriptionUpdateResult handleSubscriptionStep(Update update) {
       log.debug("HANDLING SUBSCRIPTION UPDATE");
       return new SubscriptionUpdateResult("HANDLING SUBSCRIPTION UPDATE");
    }
}
