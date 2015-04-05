package com.appdirect.integration.controllers;

import com.appdirect.integration.models.*;
import com.appdirect.integration.models.events.CancelSubscriptionOrderEvent;
import com.appdirect.integration.models.events.ChangeSubscriptionOrderEvent;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.StatusSubscriptionOrderEvent;
import com.appdirect.integration.services.EventDataRetrieverService;
import com.appdirect.integration.services.EventsService;
import com.appdirect.integration.services.SubscriptionsService;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ROLE_OAUTH')")
@RequestMapping("/api/events/subscriptions")
public class SubscriptionsAPIController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionsAPIController.class);
    private EventDataRetrieverService eventDataRetrieverService;
    private SubscriptionsService subscriptionsService;
    private EventsService eventsService;

    @Autowired
    public SubscriptionsAPIController(EventDataRetrieverService eventDataRetrieverService, SubscriptionsService subscriptionsService, EventsService eventsService) {
        this.eventDataRetrieverService = eventDataRetrieverService;
        this.subscriptionsService = subscriptionsService;
        this.eventsService = eventsService;
    }

    @RequestMapping(value = "/create", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CreateSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CreateSubscriptionOrderEvent.class);

        if (eventData == null) {
            new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }

        logger.info("{}", eventData);
        Subscription subscription = createSubscription(eventData);
        String accountIdentifier = subscriptionsService.save(subscription);
        eventsService.saveEvent(eventData);
        return new SuccessResponseMessage("toto", accountIdentifier);
    }

    @RequestMapping(value = "/change", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleChangeSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        ChangeSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, ChangeSubscriptionOrderEvent.class);

        if (eventData == null) {
            new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }

        logger.info("{}", eventData);
        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        subscriptionsService.update(accountIdentifier, eventData.getPayload().getOrder());
        eventsService.saveEvent(eventData);
        return new SuccessResponseMessage("toto", accountIdentifier);
    }

    @RequestMapping(value = "/cancel", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCancelSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CancelSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CancelSubscriptionOrderEvent.class);

        if (eventData == null) {
            new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }

        logger.info("{}", eventData);
        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        subscriptionsService.delete(accountIdentifier);
        eventsService.saveEvent(eventData);
        return new SuccessResponseMessage("toto", null);
    }

    @RequestMapping(value = "/status", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleStatusSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        StatusSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, StatusSubscriptionOrderEvent.class);

        if (eventData == null) {
            new ErrorResponseMessage(ACCOUNT_NOT_FOUND, "toto");
        }

        logger.info("{}", eventData);
        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.valueOf(eventData.getPayload().getNotice().getType().name());

        subscriptionsService.changeStatus(accountIdentifier, subscriptionStatus);
        return new SuccessResponseMessage("toto", accountIdentifier);
    }

    private Subscription createSubscription(CreateSubscriptionOrderEvent eventData) {
        Subscription subscription = new Subscription();
        subscription.setId(eventData.getPayload().getCompany().getUuid());
        subscription.setCompanyName(eventData.getPayload().getCompany().getName());
        subscription.setOrder(eventData.getPayload().getOrder());
        subscription.setEditionCode(eventData.getPayload().getOrder().getEditionCode());
        return subscription;
    }
}
