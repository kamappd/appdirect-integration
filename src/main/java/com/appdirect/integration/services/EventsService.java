package com.appdirect.integration.services;

import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.Event;
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
public class EventsService implements Consumer<reactor.event.Event<Event>> {

    private static Logger logger = LoggerFactory.getLogger(EventsService.class);

    private List<Event> events;
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Reactor publisher;

    @Autowired
    public EventsService(Reactor publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void initialize() throws FileNotFoundException {

        publisher.on($("event"), this);

        loadEvents();
    }

    private void loadEvents() {
        file = new File("events.json");
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                ObjectMapper objectMapper = new ObjectMapper();
                events = objectMapper.readValue(is, List.class);
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
            objectMapper.writeValue(is, events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(reactor.event.Event<Event> eventWrapper) {
        logger.info("handle event {}", eventWrapper.getData());
        events.add(eventWrapper.getData());
    }

    public List<Event> getEvents() {
        return new ArrayList<>(this.events);
    }

    public void createFakeEvent() {
        CreateSubscriptionOrderEvent eventData = new CreateSubscriptionOrderEvent();
        publisher.notify("event", reactor.event.Event.wrap(eventData));

    }
}
