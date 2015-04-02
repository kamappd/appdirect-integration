package com.appdirect.integration.controllers;

import com.appdirect.integration.exceptions.SubscriptionOrderEventException;
import com.appdirect.integration.services.EventDataRetrieverService;
import com.appdirect.integration.models.ErrorResponseMessage;
import com.appdirect.integration.models.SuccessResponseMessage;
import com.appdirect.integration.models.events.CancelSubscriptionOrderEvent;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static com.appdirect.integration.models.ResponseErrorCode.ACCOUNT_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping("/api/events/subscriptions")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    private EventDataRetrieverService eventDataRetrieverService;

    @Autowired
    public SubscriptionController(EventDataRetrieverService eventDataRetrieverService) {
        this.eventDataRetrieverService = eventDataRetrieverService;
    }

    @RequestMapping(value = "/create", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public SuccessResponseMessage handleCreateSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CreateSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CreateSubscriptionOrderEvent.class);

        if (eventData == null) {
            throw new SubscriptionOrderEventException(new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto"));
        }

        logger.info("{}", eventData);
        return new SuccessResponseMessage("toto", "1234");
    }

    @RequestMapping(value = "/cancel", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public SuccessResponseMessage handleCancelSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CancelSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CancelSubscriptionOrderEvent.class);

        if (eventData == null) {
            throw new SubscriptionOrderEventException(new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto"));
        }

        logger.info("{}", eventData);
        return new SuccessResponseMessage("toto", "1234");
    }

    @ExceptionHandler(SubscriptionOrderEventException.class)
    @ResponseBody
    private ErrorResponseMessage handleCreateSubscriptionOrderEventException(SubscriptionOrderEventException e) {
        return e.getErrorResponseMessage();
    }
}
