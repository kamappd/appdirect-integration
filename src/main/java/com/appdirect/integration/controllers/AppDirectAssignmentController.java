package com.appdirect.integration.controllers;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.models.ResponseMessage;
import com.appdirect.integration.models.SuccessResponseMessage;
import com.appdirect.integration.models.User;
import com.appdirect.integration.models.events.Contact;
import com.appdirect.integration.models.events.UserAssignmentEvent;
import com.appdirect.integration.models.events.UserUnassignmentEvent;
import com.appdirect.integration.services.CompanyService;
import com.appdirect.integration.services.EventDataRetrieverService;
import com.appdirect.integration.services.EventsService;
import com.appdirect.integration.services.UserService;
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

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@PreAuthorize("hasRole('ROLE_OAUTH')")
@RequestMapping("/api/events/assignment")
public class AppDirectAssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppDirectAssignmentController.class);
    private EventDataRetrieverService eventDataRetrieverService;
    private CompanyService companyService;
    private UserService userService;
    private EventsService eventsService;

    @Autowired
    public AppDirectAssignmentController(EventDataRetrieverService eventDataRetrieverService, CompanyService companyService, UserService userService, EventsService eventsService) {
        this.eventDataRetrieverService = eventDataRetrieverService;
        this.companyService = companyService;
        this.userService = userService;
        this.eventsService = eventsService;
    }

    @RequestMapping(value = "/assign", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        UserAssignmentEvent eventData = eventDataRetrieverService.getEventData(url, UserAssignmentEvent.class);

        logger.info("{}", eventData);
        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        Company company = companyService.getCompany(accountIdentifier);
        User user = createUser(eventData.getPayload().getUser(), company);
        userService.save(user);
        eventsService.saveEvent(eventData);
        return new SuccessResponseMessage();
    }

    @RequestMapping(value = "/unassign", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCancelSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        UserUnassignmentEvent eventData = eventDataRetrieverService.getEventData(url, UserUnassignmentEvent.class);

        String userOpenId = eventData.getPayload().getUser().getOpenId();
        userService.deleteByOpenId(userOpenId);
        eventsService.saveEvent(eventData);
        return new SuccessResponseMessage();
    }

    private User createUser(Contact contact, Company company) {
        User user = new User();
        user.setCompany(company);
        user.setEmail(contact.getEmail());
        user.setFirstName(contact.getFirstName());
        user.setLastName(contact.getLastName());
        user.setOpenId(contact.getOpenId());
        return user;
    }
}
