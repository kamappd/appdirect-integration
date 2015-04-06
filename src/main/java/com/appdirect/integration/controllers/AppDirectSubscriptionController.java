package com.appdirect.integration.controllers;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.models.*;
import com.appdirect.integration.models.events.*;
import com.appdirect.integration.services.CompanyService;
import com.appdirect.integration.services.EventDataRetrieverService;
import com.appdirect.integration.services.UserService;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
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
@RequestMapping("/api/events/subscriptions")
public class AppDirectSubscriptionController {

    private EventDataRetrieverService eventDataRetrieverService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired
    public AppDirectSubscriptionController(EventDataRetrieverService eventDataRetrieverService, CompanyService companyService, UserService userService) {
        this.eventDataRetrieverService = eventDataRetrieverService;
        this.companyService = companyService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCreateSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CreateSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CreateSubscriptionOrderEvent.class);

        Company company = createCompany(eventData);
        User user = createUser(eventData.getCreator(), company);
        String accountIdentifier = companyService.save(company);
        userService.save(user);
        return new SuccessResponseMessage(accountIdentifier);
    }

    @RequestMapping(value = "/change", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleChangeSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        ChangeSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, ChangeSubscriptionOrderEvent.class);

        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        EditionCode editionCode = eventData.getPayload().getOrder().getEditionCode();
        companyService.updateEdition(accountIdentifier, editionCode);
        return new SuccessResponseMessage(accountIdentifier);
    }

    @RequestMapping(value = "/cancel", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleCancelSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        CancelSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, CancelSubscriptionOrderEvent.class);

        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        companyService.delete(accountIdentifier);
        return new SuccessResponseMessage();
    }

    @RequestMapping(value = "/status", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseMessage handleStatusSubscriptionOrderEvent(@RequestParam("url") String url) throws IOException, JAXBException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
        StatusSubscriptionOrderEvent eventData = eventDataRetrieverService.getEventData(url, StatusSubscriptionOrderEvent.class);

        String accountIdentifier = eventData.getPayload().getAccount().getAccountIdentifier();
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.valueOf(eventData.getPayload().getNotice().getType().name());

        companyService.changeStatus(accountIdentifier, subscriptionStatus);
        return new SuccessResponseMessage(accountIdentifier);
    }

    private Company createCompany(CreateSubscriptionOrderEvent eventData) {
        Company company = new Company();
        company.setName(eventData.getPayload().getCompany().getName());
        company.setEditionCode(eventData.getPayload().getOrder().getEditionCode());
        return company;
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
