package com.appdirect.integration.controllers;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.models.ResponseMessage;
import com.appdirect.integration.models.User;
import com.appdirect.integration.models.events.*;
import com.appdirect.integration.services.CompanyService;
import com.appdirect.integration.services.EventDataRetrieverService;
import com.appdirect.integration.services.EventsService;
import com.appdirect.integration.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * I should also test error cases
 */
@RunWith(MockitoJUnitRunner.class)
public class AppDirectAssignmentControllerTest {

    @InjectMocks
    private AppDirectAssignmentController appDirectAssignmentController;
    @Mock
    private EventDataRetrieverService eventDataRetrieverService;
    @Mock
    private CompanyService companyService;
    @Mock
    private UserService userService;
    @Mock
    private EventsService eventsService;

    @Test
    public void can_handle_user_assignment_event() throws Exception {

        // Given
        String url = "http://somewhere.com";
        UserAssignmentEvent userAssignmentEvent = aBasicUserAssignmentEvent("acountId", "123456789");
        when(eventDataRetrieverService.getEventData(url, UserAssignmentEvent.class)).thenReturn(userAssignmentEvent);
        when(companyService.getCompany("acountId")).thenReturn(aCompany("acountId"));

        // Execute
        ResponseMessage responseMessage = appDirectAssignmentController.handleUserAssignmentEvent(url);

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));
        verifyUserSaved();
        verify(eventsService).saveEvent(userAssignmentEvent);
    }

    @Test
    public void can_handle_user_unassignment_event() throws Exception {

        // Given
        String url = "http://somewhere.com";
        UserUnassignmentEvent accountId = aBasicUserUnassignmentEvent("accountId", "123456789");
        when(eventDataRetrieverService.getEventData(url, UserUnassignmentEvent.class)).thenReturn(accountId);
        when(companyService.getCompany("accountId")).thenReturn(aCompany("accountId"));

        // Execute
        ResponseMessage responseMessage = appDirectAssignmentController.handlUserUnassignmentEvent(url);

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));

        verify(userService).deleteByOpenId("123456789");
        verify(eventsService).saveEvent(accountId);
    }

    private UserAssignmentEvent aBasicUserAssignmentEvent(String accountId, String openId) {
        UserAssignmentEvent userAssignmentEvent = new UserAssignmentEvent();
        UserAssignmentPayload payload = new UserAssignmentPayload();
        payload.setAccount(aBasicAccount(accountId));
        payload.setUser(aBasicUser(openId));
        userAssignmentEvent.setPayload(payload);
        return userAssignmentEvent;
    }

    private UserUnassignmentEvent aBasicUserUnassignmentEvent(String accountId, String openId) {
        UserUnassignmentEvent userUnassignmentEvent = new UserUnassignmentEvent();
        UserUnassignmentPayload payload = new UserUnassignmentPayload();
        payload.setAccount(aBasicAccount(accountId));
        payload.setUser(aBasicUser(openId));
        userUnassignmentEvent.setPayload(payload);
        return userUnassignmentEvent;
    }

    private Account aBasicAccount(String accountId) {
        Account account = new Account();
        account.setAccountIdentifier(accountId);
        return account;
    }

    private Contact aBasicUser(String openId) {
        Contact contact = new Contact();
        contact.setOpenId(openId);
        return contact;
    }

    private Company aCompany(String accountId) {
        Company company = new Company();
        company.setId(accountId);
        return company;
    }

    private void verifyUserSaved() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(user.getOpenId(), is("123456789"));
    }
}