package com.appdirect.integration.services;

import com.appdirect.integration.models.Subscription;
import com.appdirect.integration.models.SubscriptionStatus;
import com.appdirect.integration.models.events.Order;
import com.appdirect.integration.utils.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionsService {

    protected Map<String, Subscription> subscriptions = new HashMap<>();
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private IdGenerator idGenerator;

    @Autowired
    public SubscriptionsService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @PostConstruct
    public void initialize() throws FileNotFoundException {
        loadSubscribers();
    }

    @PreDestroy
    public void persist() {
        saveEvents();
    }

    public String save(Subscription subscription) {
        String id = idGenerator.generateId();
        subscription.setId(id);
        subscriptions.put(id, subscription);
        return id;
    }

    public List<Subscription> getSubscriptions() {
        return new ArrayList<>(this.subscriptions.values());
    }

    public void update(String identifier, Order order) {
        Subscription subscription = subscriptions.get(identifier);
        subscription.setOrder(order);
    }

    public void delete(String identifier) {
        subscriptions.remove(identifier);
    }

    public void changeStatus(String identifier, SubscriptionStatus status) {
        Subscription subscription = subscriptions.get(identifier);
        subscription.setStatus(status);
    }

    private void loadSubscribers() {
        file = new File("subscriptions.json");
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                ObjectMapper objectMapper = new ObjectMapper();
                subscriptions = objectMapper.readValue(is, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveEvents() {
        try (OutputStream is = new FileOutputStream(file)) {
            objectMapper.writeValue(is, subscriptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
