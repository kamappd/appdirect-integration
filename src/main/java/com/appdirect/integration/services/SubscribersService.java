package com.appdirect.integration.services;

import com.appdirect.integration.models.events.Event;
import com.appdirect.integration.models.events.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SubscribersService implements Consumer<reactor.event.Event<Event>> {

    private static Logger logger = LoggerFactory.getLogger(SubscribersService.class);

    private List<Event> subscribers = new ArrayList<>();
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Reactor publisher;

    @Autowired
    public SubscribersService(Reactor publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void initialize() throws FileNotFoundException {

        publisher.on($("event"), this);

        loadSubscribers();
    }

    private void loadSubscribers() {
        file = new File("subscribers.json");
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                ObjectMapper objectMapper = new ObjectMapper();
                subscribers = objectMapper.readValue(is, List.class);
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
            objectMapper.writeValue(is, subscribers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(reactor.event.Event<Event> eventWrapper) {
        Event event = eventWrapper.getData();
        if (event.getType() == EventType.SUBSCRIPTION_ORDER) {

        } else if (event.getType() == EventType.SUBSCRIPTION_CANCEL) {
            subscribers.add(event);
        } else {
            subscribers.remove(event);
        }
    }

    public List<Event> getSubscribers() {
        return new ArrayList<>(this.subscribers);
    }
}
