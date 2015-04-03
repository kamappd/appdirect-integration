package com.appdirect.integration.controllers;


import com.appdirect.integration.models.ResponseMessage;
import com.appdirect.integration.models.SuccessResponseMessage;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.Event;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.Reactor;
import reactor.function.Consumer;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static reactor.event.selector.Selectors.$;

@Controller
public class EventsController implements Consumer<reactor.event.Event<Event>> {

    private static Logger logger = LoggerFactory.getLogger(EventsController.class);
    @Autowired
    private Reactor publisher;

    private List<Event> events = new ArrayList<>();

    @PostConstruct
    public void configurePublisher() {
        publisher.on($("event"), this);

    }

    @MessageMapping("/events")
    @SendTo("/topic/events")
    @RequestMapping(value = "/test/get", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Event> getEvents() throws Exception {
        return events;
    }

    @RequestMapping(value = "/test/create", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent() throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CreateSubscriptionOrderEvent eventData = new CreateSubscriptionOrderEvent();
        publisher.notify("event", reactor.event.Event.wrap(eventData));
        return new SuccessResponseMessage("toto", "1234");
    }

    @Override
    public void accept(reactor.event.Event<Event> eventWrapper) {
        logger.info("handle event {}", eventWrapper.getData());
        events.add(eventWrapper.getData());
    }
}
