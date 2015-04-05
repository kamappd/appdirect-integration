package com.appdirect.integration.services;

import com.appdirect.integration.models.Subscription;
import com.appdirect.integration.models.events.ChangeSubscriptionOrderEvent;
import com.appdirect.integration.models.events.ChangeSubscriptionOrderPayload;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import com.appdirect.integration.models.events.CreateSubscriptionOrderPayload;
import com.appdirect.integration.models.events.common.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.event.Event;

import static com.appdirect.integration.models.events.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.integration.models.events.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.integration.models.events.common.EditionType.BASIC;
import static com.appdirect.integration.models.events.common.EditionType.PREMIUM;
import static com.appdirect.integration.models.events.common.OrderUnit.MEGABYTE;
import static com.appdirect.integration.models.events.common.OrderUnit.USER;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionsServiceTest {

    @InjectMocks
    private SubscriptionsService subscribersService;

    @Test
    public void when_receiving_new_order__add_subscription() throws Exception {
        // Given
        CreateSubscriptionOrderEvent createSubscriptionOrderEvent = aStandardCreateSubscriptionOrder();

        // Execute
        subscribersService.accept(Event.wrap(createSubscriptionOrderEvent));

        // Verify
        assertThat(subscribersService.getSubscriptions(), contains(createSubscriptionOrderEvent));
    }

    @Test
    public void when_receiving_change_subscription__update_subscription() throws Exception {
        // Given
        subscribersService.subscriptions.add(aBasicSubscription());
        ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent = aChangeSubscriptionOrderEvent();

        // Execute
        subscribersService.accept(Event.wrap(changeSubscriptionOrderEvent));

        // Verify
//        verifySubscriberUpdated(createSubscriptionOrderEvent, changeSubscriptionOrderEvent);
    }

    @Test
    public void when_receiving_cancel_subscription__remove_subscription() throws Exception {

    }

    private Subscription aBasicSubscription() {
        Subscription subscription = new Subscription();
        subscription.setId("1");
        return subscription;
    }

    private CreateSubscriptionOrderEvent aStandardCreateSubscriptionOrder() {
        CreateSubscriptionOrderEvent event = new CreateSubscriptionOrderEvent();
        event.setType(SUBSCRIPTION_ORDER);
        event.setMarketplace(aStandardMarketPlace());
        event.setCreator(aStandardCreator());
        event.setPayload(aStandardCreateSubscriptionOrderPayload());
        return event;
    }

    private ChangeSubscriptionOrderEvent aChangeSubscriptionOrderEvent() {
        ChangeSubscriptionOrderEvent event = new ChangeSubscriptionOrderEvent();
        event.setType(SUBSCRIPTION_CHANGE);
        event.setMarketplace(aStandardMarketPlace());
        event.setCreator(aStandardCreator());
        event.setPayload(aChangeSubscriptionOrderPayload());
        return event;
    }

    private MarketPlace aStandardMarketPlace() {
        MarketPlace marketPlace = new MarketPlace();
        marketPlace.setBaseUrl("http://somewhere_over_the_rain.bow");
        marketPlace.setPartner("mate");
        return marketPlace;
    }

    private Contact aStandardCreator() {
        Contact contact = new Contact();
        contact.setFirstName("isreal");
        contact.setLastName("kamkawiwo'ole");
        contact.setLanguage("en");
        contact.setEmail("somewhere@rain.bow");
        contact.setOpenId("AFECDB349827423A");
        return contact;
    }

    private CreateSubscriptionOrderPayload aStandardCreateSubscriptionOrderPayload() {
        CreateSubscriptionOrderPayload payload = new CreateSubscriptionOrderPayload();
        payload.setOrder(aBasicOrder());
        payload.setCompany(aStandardCompany());
        return payload;
    }

    private ChangeSubscriptionOrderPayload aChangeSubscriptionOrderPayload() {
        ChangeSubscriptionOrderPayload payload = new ChangeSubscriptionOrderPayload();
        payload.setAccount(aBasicAccount());
        payload.setOrder(aPremiumOrder());
        return payload;
    }

    private Order aBasicOrder() {
        return anOrder(BASIC, 10, 15);
    }

    private Order aPremiumOrder() {
        return anOrder(PREMIUM, 25, 250);
    }

    private Order anOrder(EditionType editionType, int quantityUser, int quantityMegabyte) {
        Order order = new Order();
        order.setEditionCode(editionType);
        order.setItems(asList(anOrderItem(quantityUser, USER), anOrderItem(quantityMegabyte, MEGABYTE)));
        return order;
    }

    private OrderItem anOrderItem(int quantity, OrderUnit orderUnitser) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setUnit(orderUnitser);
        return orderItem;
    }


    private Company aStandardCompany() {
        Company company = new Company();
        company.setName("sun");
        company.setPhoneNumber("1(555)444-3333");
        company.setUuid("1");
        company.setWebsite("http://could.sky.net");
        company.setEmail("cloud@sky.net");
        return company;
    }

    private Account aBasicAccount() {
        Account account = new Account();
        account.setAccountIdentifier("1");
        return account;
    }

//    private void verifySubscriberUpdated(CreateSubscriptionOrderEvent createSubscriptionOrderEvent, ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent) {
//        assertThat(subscribersService.getSubscriptions(), hasSize(1));
//        com.appdirect.integration.models.events.Event event = subscribersService.getSubscriptions().get(0);
//        assertThat(event, inInstanceOf(C));
//        assertThat(event, contains(changeSubscriptionOrderEvent));
//    }

}