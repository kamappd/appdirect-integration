package com.appdirect.integration.controllers;

import com.appdirect.integration.EventDataRetrieverService;
import com.appdirect.integration.models.ErrorResponseMessage;
import com.appdirect.integration.models.ResponseMessage;
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
    @ResponseBody()
    public ResponseMessage handleCreateSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        url = "https://www.appdirect.com/api/integration/v1/events/e5be7b7f-88f1-405f-9f41-2cf82bb2c9d7";
        CreateSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CreateSubscriptionOrderEvent.class);
        if (eventData == null) {
            return new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }
        logger.info("{}", eventData);
        return new SuccessResponseMessage("toto", "1234");
    }

    @RequestMapping(value = "/cancel", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody()
    public ResponseMessage handleCancelSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        url = "https://www.appdirect.com/api/integration/v1/events/e5be7b7f-88f1-405f-9f41-2cf82bb2c9d7";
        CancelSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CancelSubscriptionOrderEvent.class);
        if (eventData == null) {
            return new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }
        logger.info("{}", eventData);
        return new SuccessResponseMessage("toto", "1234");
    }

}
