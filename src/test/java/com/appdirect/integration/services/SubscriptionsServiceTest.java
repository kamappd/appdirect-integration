package com.appdirect.integration.services;

import com.appdirect.integration.models.Subscription;
import com.appdirect.integration.models.events.EditionType;
import com.appdirect.integration.models.events.Order;
import com.appdirect.integration.models.events.OrderItem;
import com.appdirect.integration.models.events.OrderUnit;
import com.appdirect.integration.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.appdirect.integration.models.events.EditionType.BASIC;
import static com.appdirect.integration.models.events.EditionType.PREMIUM;
import static com.appdirect.integration.models.events.OrderUnit.MEGABYTE;
import static com.appdirect.integration.models.events.OrderUnit.USER;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionsServiceTest {

    @InjectMocks
    private SubscriptionsService subscribersService;
    @Mock
    private IdGenerator idGenerator;

    @Test
    public void when_receiving_new_order__add_subscription() throws Exception {

        // Given
        when(idGenerator.generateId()).thenReturn("1234");
        Subscription subscription = aBasicSubscription("1234");

        // Execute
        subscribersService.save(subscription);

        // Verify
        assertThat(subscribersService.getSubscriptions(), contains(subscription));
    }

    @Test
    public void when_receiving_change_subscription__update_subscription() throws Exception {

        // Given
        givenASubscriptionAlreadyExists("1234");

        // Execute
        subscribersService.update("1234", aPremiumOrder());

        // Verify
        Subscription expectedSubscription = aBasicSubscription("1234");
        expectedSubscription.setOrder(aPremiumOrder());
        assertThat(subscribersService.getSubscriptions(), contains(expectedSubscription));
    }

    @Test
    public void when_receiving_cancel_subscription__remove_subscription() throws Exception {

        // Given
        givenASubscriptionAlreadyExists("1234");

        // Execute
        subscribersService.delete("1234");

        // Verify
        assertThat(subscribersService.getSubscriptions(), hasSize(0));
    }

    private Subscription aBasicSubscription(String id) {
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setCompanyName("aCompany");
        subscription.setOrder(aBasicOrder());
        return subscription;
    }

    private void givenASubscriptionAlreadyExists(String id) {
        subscribersService.subscriptions.put(id, aBasicSubscription(id));
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
}