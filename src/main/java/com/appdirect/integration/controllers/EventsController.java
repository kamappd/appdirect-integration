package com.appdirect.integration.controllers;


import com.appdirect.integration.models.ResponseMessage;
import com.appdirect.integration.models.SuccessResponseMessage;
import com.appdirect.integration.models.events.Event;
import com.appdirect.integration.services.EventsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class EventsController {

    private EventsService eventsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @RequestMapping(value = "/subscriptions", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Event> getEvents() throws Exception {
        return eventsService.getEvents();
    }


    @RequestMapping(value = "/test/create", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent() throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        eventsService.createFakeEvent();
        return new SuccessResponseMessage("toto", "1234");
    }
}
