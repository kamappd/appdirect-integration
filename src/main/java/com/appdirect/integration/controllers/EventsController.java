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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class EventsController extends TextWebSocketHandler {

    private EventsService eventsService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

//    @MessageMapping("/events")
//    @SendTo("/topic/events")
    @RequestMapping(value = "/events", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Event> getEvents() throws Exception {
        return eventsService.getEvents();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(eventsService.getEvents())));
    }

    @RequestMapping(value = "/test/create", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent() throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        eventsService.createFakeEvent();
        return new SuccessResponseMessage("toto", "1234");
    }
}
