package com.appdirect.integration.controllers;

import com.appdirect.integration.EventDataRetrieverService;
import com.appdirect.integration.models.ResponseMessage;
import com.appdirect.integration.models.SubscriptionOrderEvent;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping("/api/integration/v1/events/subscriptions")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    private EventDataRetrieverService eventDataRetrieverService;

    @Autowired
    public SubscriptionController(EventDataRetrieverService eventDataRetrieverService) {
        this.eventDataRetrieverService = eventDataRetrieverService;
    }

    @RequestMapping(value = "/", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody()
    public ResponseMessage handleSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        SubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, SubscriptionOrderEvent.class);
        logger.info("{}", eventData);
        return new ResponseMessage(true, "toto", "1234");
    }

}
