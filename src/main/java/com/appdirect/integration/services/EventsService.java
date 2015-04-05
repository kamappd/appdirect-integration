package com.appdirect.integration.services;

import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {

    private List<Event> events = new ArrayList<>();
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initialize() throws FileNotFoundException {
        loadEvents();
    }

    @PreDestroy
    public void persist() {
        saveEvents();
    }

    public List<Event> getEvents() {
        return new ArrayList<>(this.events);
    }

    public void createFakeEvent() {
        events.add(new CreateSubscriptionOrderEvent());
    }

    public void saveEvent(Event event) {
        events.add(event);
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

    private void saveEvents() {
        try (OutputStream is = new FileOutputStream(file)) {
            objectMapper.writeValue(is, events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
