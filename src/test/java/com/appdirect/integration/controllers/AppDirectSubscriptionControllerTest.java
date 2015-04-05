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

import static com.appdirect.integration.models.EditionCode.BASIC;
import static com.appdirect.integration.models.EditionCode.PREMIUM;
import static com.appdirect.integration.models.SubscriptionStatus.ACTIVE;
import static com.appdirect.integration.models.SubscriptionStatus.DEACTIVATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * I should also test error cases
 */
@RunWith(MockitoJUnitRunner.class)
public class AppDirectSubscriptionControllerTest {

    @InjectMocks
    private AppDirectSubscriptionController appDirectSubscriptionController;
    @Mock
    private EventDataRetrieverService eventDataRetrieverService;
    @Mock
    private CompanyService companyService;
    @Mock
    private UserService userService;
    @Mock
    private EventsService eventsService;

    @Test
    public void can_handle_create_order_subscription_event() throws Exception {

        // Given
        CreateSubscriptionOrderEvent createSubscriptionOrderEvent = aBasicCreateSubscriptionOrderEvent("123456789");
        when(eventDataRetrieverService.getEventData("http://somewhere.com", CreateSubscriptionOrderEvent.class)).thenReturn(createSubscriptionOrderEvent);

        // Execute
        ResponseMessage responseMessage = appDirectSubscriptionController.handleCreateSubscriptionOrderEvent("http://somewhere.com");

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));
        Company company = verifyCompanySaved();
        verifyUserSaved(company);
        verify(eventsService).saveEvent(createSubscriptionOrderEvent);
    }

    @Test
    public void can_handle_cancel_order_subscription_event() throws Exception {

        // Given
        CancelSubscriptionOrderEvent cancelSubscriptionOrderEvent = aBasicCancelSubscriptionOrderEvent("1234", "123456789");
        when(eventDataRetrieverService.getEventData("http://somewhere.com", CancelSubscriptionOrderEvent.class)).thenReturn(cancelSubscriptionOrderEvent);

        // Execute
        ResponseMessage responseMessage = appDirectSubscriptionController.handleCancelSubscriptionOrderEvent("http://somewhere.com");

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));
        verify(companyService).delete("1234");
        verify(eventsService).saveEvent(cancelSubscriptionOrderEvent);
    }

    @Test
    public void can_handle_change_order_subscription_event() throws Exception {

        // Given
        ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent = aBasicChangeSubscriptionOrderEvent("1234");
        when(eventDataRetrieverService.getEventData("http://somewhere.com", ChangeSubscriptionOrderEvent.class)).thenReturn(changeSubscriptionOrderEvent);

        // Execute
        ResponseMessage responseMessage = appDirectSubscriptionController.handleChangeSubscriptionOrderEvent("http://somewhere.com");

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));
        verify(companyService).updateEdition("1234", PREMIUM);
        verify(eventsService).saveEvent(changeSubscriptionOrderEvent);
    }

    @Test
    public void can_handle_status_order_subscription_event() throws Exception {

        // Given
        StatusSubscriptionOrderEvent statusSubscriptionOrderEvent = aBasicStatusSubscriptionOrderEvent("1234");
        when(eventDataRetrieverService.getEventData("http://somewhere.com", StatusSubscriptionOrderEvent.class)).thenReturn(statusSubscriptionOrderEvent);

        // Execute
        ResponseMessage responseMessage = appDirectSubscriptionController.handleStatusSubscriptionOrderEvent("http://somewhere.com");

        // Verify
        assertThat(responseMessage.isSuccess(), is(true));
        verify(companyService).changeStatus("1234", DEACTIVATED);
        verify(eventsService).saveEvent(statusSubscriptionOrderEvent);
    }

    private ChangeSubscriptionOrderEvent aBasicChangeSubscriptionOrderEvent(String accountIdentifier) {
        ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent = new ChangeSubscriptionOrderEvent();
        changeSubscriptionOrderEvent.setPayload(aBasicChangeSubscriptionOrderPayload(accountIdentifier));
        return changeSubscriptionOrderEvent;
    }

    private CreateSubscriptionOrderEvent aBasicCreateSubscriptionOrderEvent(String openId) {
        CreateSubscriptionOrderEvent createSubscriptionOrderEvent = new CreateSubscriptionOrderEvent();
        createSubscriptionOrderEvent.setCreator(aBasicUser(openId));
        createSubscriptionOrderEvent.setPayload(aCreateSubscriptionOrderPayload());
        return createSubscriptionOrderEvent;
    }

    private CancelSubscriptionOrderEvent aBasicCancelSubscriptionOrderEvent(String accountIdentifier, String openId) {
        CancelSubscriptionOrderEvent cancelSubscriptionOrderEvent = new CancelSubscriptionOrderEvent();
        cancelSubscriptionOrderEvent.setCreator(aBasicUser(openId));
        cancelSubscriptionOrderEvent.setPayload(aCancelSubscriptionOrderPayload(accountIdentifier));
        return cancelSubscriptionOrderEvent;
    }

    private StatusSubscriptionOrderEvent aBasicStatusSubscriptionOrderEvent(String accountIdentifier) {
        StatusSubscriptionOrderEvent statusSubscriptionOrderEvent = new StatusSubscriptionOrderEvent();
        statusSubscriptionOrderEvent.setPayload(aBasicStatusSubscriptionOrderPayload(accountIdentifier));
        return statusSubscriptionOrderEvent;
    }

    private Contact aBasicUser(String openId) {
        Contact contact = new Contact();
        contact.setOpenId(openId);
        return contact;
    }

    private CreateSubscriptionOrderPayload aCreateSubscriptionOrderPayload() {
        CreateSubscriptionOrderPayload createSubscriptionOrderPayload = new CreateSubscriptionOrderPayload();
        createSubscriptionOrderPayload.setCompany(aBasicCompanyEvent());
        createSubscriptionOrderPayload.setOrder(aBasicOrder());
        return createSubscriptionOrderPayload;
    }

    private CancelSubscriptionOrderPayload aCancelSubscriptionOrderPayload(String accountIdentifier) {
        CancelSubscriptionOrderPayload cancelSubscriptionOrderPayload = new CancelSubscriptionOrderPayload();
        cancelSubscriptionOrderPayload.setAccount(aBasicAccount(accountIdentifier));
        return cancelSubscriptionOrderPayload;
    }

    private ChangeSubscriptionOrderPayload aBasicChangeSubscriptionOrderPayload(String accountIdentifier) {
        ChangeSubscriptionOrderPayload changeSubscriptionOrderPayload = new ChangeSubscriptionOrderPayload();
        changeSubscriptionOrderPayload.setAccount(aBasicAccount(accountIdentifier));
        changeSubscriptionOrderPayload.setOrder(aPremiumOrder());
        return changeSubscriptionOrderPayload;
    }

    private StatusSubscriptionOrderPayload aBasicStatusSubscriptionOrderPayload(String accountIdentifier) {
        StatusSubscriptionOrderPayload statusSubscriptionOrderPayload = new StatusSubscriptionOrderPayload();
        statusSubscriptionOrderPayload.setAccount(aBasicAccount(accountIdentifier));
        statusSubscriptionOrderPayload.setNotice(aDeactivatedNotice());
        return statusSubscriptionOrderPayload;
    }

    private com.appdirect.integration.models.events.Company aBasicCompanyEvent() {
        com.appdirect.integration.models.events.Company company = new com.appdirect.integration.models.events.Company();
        company.setName("myCompany");
        return company;
    }

    private Order aBasicOrder() {
        Order order = new Order();
        order.setEditionCode(BASIC);
        return order;
    }

    private Order aPremiumOrder() {
        Order order = new Order();
        order.setEditionCode(PREMIUM);
        return order;
    }

    private Account aBasicAccount(String accountIdentifier) {
        Account account = new Account();
        account.setAccountIdentifier(accountIdentifier);
        return account;
    }

    private Notice aDeactivatedNotice() {
        Notice notice = new Notice();
        notice.setType(NoticeType.DEACTIVATED);
        return notice;
    }

    private Company verifyCompanySaved() {
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyService).save(companyArgumentCaptor.capture());
        Company company = companyArgumentCaptor.getValue();
        assertThat(company.getEditionCode(), is(BASIC));
        assertThat(company.getName(), is("myCompany"));
        assertThat(company.getStatus(), is(ACTIVE));
        return company;
    }

    private void verifyUserSaved(Company company) {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(user.getOpenId(), is("123456789"));
        assertThat(user.getCompany(), is(company));
    }


}