package com.appdirect.integration.controllers;


import com.appdirect.integration.models.events.Event;
import com.appdirect.integration.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class EventsController {

    private EventsService eventsService;

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @RequestMapping(value = "/events", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Event> getEvents() throws Exception {
        return eventsService.getEvents();
    }
}
