package com.topov.estatesearcher.telegram.state;

import com.topov.estatesearcher.telegram.Hint;
import com.topov.estatesearcher.telegram.Keyboard;
import com.topov.estatesearcher.telegram.UpdateResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.*;

@Log4j2
@Service
public class SubscriptionBotState extends AbstractBotState {

    private final List<AbstractSubscriptionStep> subscriptionSteps;

    @Autowired
    public SubscriptionBotState(List<AbstractSubscriptionStep> subscriptionSteps) {
        super(StateName.SUBSCRIPTION);
        this.subscriptionSteps = subscriptionSteps;
    }

    @PostConstruct
    void orderSteps() {
        subscriptionSteps.sort(new SubscriptionStepComparator());
    }

    @Override
    public UpdateResult handleUpdate(Update update) {
        log.debug("Your in subscription creation state");
        return new UpdateResult("SUBSCRIPTION CREATION STATE");
    }

    @Override
    public Hint getHint(Update update) {
        log.debug("Your in subscription creation hint");
        return new Hint("SUBSCRIPTION CREATION HINT");
    }

    @Override
    public Keyboard getKeyboard() {
        return new Keyboard(Collections.emptyList());
    }

    private static class SubscriptionStepComparator implements Comparator<AbstractSubscriptionStep> {
        @Override
        public int compare(AbstractSubscriptionStep step1, AbstractSubscriptionStep step2) {
            return Integer.compare(step1.getStepOrder(), step2.getStepOrder());
        }
    }
}
