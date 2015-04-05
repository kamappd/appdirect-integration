package com.appdirect.integration.services;

import com.appdirect.integration.models.Subscription;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Reactor;
import reactor.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static reactor.event.selector.Selectors.$;

@Service
public class SubscriptionsService implements Consumer<reactor.event.Event<Event>> {

    protected List<Subscription> subscriptions = new ArrayList<>();
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Reactor publisher;

    @Autowired
    public SubscriptionsService(Reactor publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void initialize() throws FileNotFoundException {

        publisher.on($("event"), this);

        loadSubscribers();
    }

    private void loadSubscribers() {
        file = new File("subscriptions.json");
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                ObjectMapper objectMapper = new ObjectMapper();
                subscriptions = objectMapper.readValue(is, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void persist() {
        saveEvents();
    }

    private void saveEvents() {
        try (OutputStream is = new FileOutputStream(file)) {
            objectMapper.writeValue(is, subscriptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(reactor.event.Event<Event> eventWrapper) {
        Event event = eventWrapper.getData();
        switch (event.getType()) {
            case SUBSCRIPTION_ORDER:
                CreateSubscriptionOrderEvent createSubscriptionOrderEvent = (CreateSubscriptionOrderEvent)event;
                Subscription subscription = new Subscription();
                subscription.setId(createSubscriptionOrderEvent.getPayload().getCompany().getUuid());
                subscription.setCompanyName(createSubscriptionOrderEvent.getPayload().getCompany().getName());
                subscription.setOrder(createSubscriptionOrderEvent.getPayload().getOrder());
                subscriptions.add(subscription);
                break;
            case SUBSCRIPTION_CANCEL:
                subscriptions.remove(event);
                break;
            case SUBSCRIPTION_CHANGE:
            default:
        }
    }

    public List<Subscription> getSubscriptions() {
        return new ArrayList<>(this.subscriptions);
    }
}
