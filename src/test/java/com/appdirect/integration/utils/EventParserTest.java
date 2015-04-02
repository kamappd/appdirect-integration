package com.appdirect.integration.utils;

import com.appdirect.integration.models.events.*;
import com.appdirect.integration.models.events.common.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static com.appdirect.integration.models.events.EventType.*;
import static com.appdirect.integration.models.events.NoticeType.DEACTIVATED;
import static com.appdirect.integration.models.events.common.OrderUnit.MEGABYTE;
import static com.appdirect.integration.models.events.common.OrderUnit.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class EventParserTest {

    private EventParser eventParser = new EventParser();

    @Test
    public void can_parse_create_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aCreateSubscriptionOrderEventInputStream();

        CreateSubscriptionOrderEvent createSubscriptionOrderEvent = eventParser.parse(input, CreateSubscriptionOrderEvent.class);

        verifyCreateSubscriptionOrderEvent(createSubscriptionOrderEvent);
    }

    @Test
    public void can_parse_change_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aChangeSubscriptionOrderEventInputStream();

        ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent = eventParser.parse(input, ChangeSubscriptionOrderEvent.class);

        verifyChangeSubscriptionOrderEvent(changeSubscriptionOrderEvent);
    }

    @Test
    public void can_parse_cancel_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aCancelSubscriptionOrderEventInputStream();

        CancelSubscriptionOrderEvent cancelSubscriptionOrderEvent = eventParser.parse(input, CancelSubscriptionOrderEvent.class);

        verifyCancelSubscriptionOrderEvent(cancelSubscriptionOrderEvent);
    }

    @Test
    public void can_parse_status_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aStatusSubscriptionOrderEventInputStream();

        StatusSubscriptionOrderEvent statusSubscriptionOrderEvent = eventParser.parse(input, StatusSubscriptionOrderEvent.class);

        verifyStatusSubscriptionOrderEvent(statusSubscriptionOrderEvent);
    }

    private ByteArrayInputStream aCreateSubscriptionOrderEventInputStream() {
        return new ByteArrayInputStream(("" +
                "<event>\n" +
                "    <type>SUBSCRIPTION_ORDER</type>\n" +
                "    <marketplace>\n" +
                "        <partner>ACME</partner>\n" +
                "        <baseUrl>https://www.acme-marketplace.com</baseUrl>\n" +
                "    </marketplace>\n" +
                "    <creator>\n" +
                "        <email>andysen@gmail.com</email>\n" +
                "        <firstName>Andy</firstName>\n" +
                "        <lastName>Sen</lastName>\n" +
                "        <openId>https://www.acme-marketplace.com/openid/id/a11a7918-bb43-4429-a256-f6d729c71033</openId>\n" +
                "        <language>en</language>\n" +
                "    </creator>\n" +
                "    <payload>\n" +
                "        <company>\n" +
                "            <uuid>d15bb36e-5fb5-11e0-8c3c-00262d2cda03</uuid>\n" +
                "            <email>admin@example.com</email>\n" +
                "            <name>Example Company</name>\n" +
                "            <phoneNumber>1-415-555-1212</phoneNumber>\n" +
                "            <website>www.appdirect.com</website>\n" +
                "        </company>\n" +
                "        <order>\n" +
                "            <editionCode>BASIC</editionCode>\n" +
                "            <item>\n" +
                "                <quantity>10</quantity>\n" +
                "                <unit>USER</unit>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <quantity>15</quantity>\n" +
                "                <unit>MEGABYTE</unit>\n" +
                "            </item>\n" +
                "        </order>\n" +
                "    </payload>\n" +
                "</event>").getBytes());
    }

    private ByteArrayInputStream aChangeSubscriptionOrderEventInputStream() {
        return new ByteArrayInputStream(("" +
                "<event>\n" +
                "    <type>SUBSCRIPTION_CHANGE</type>\n" +
                "    <marketplace>\n" +
                "        <partner>ACME</partner>\n" +
                "        <baseUrl>https://www.acme-marketplace.com</baseUrl>\n" +
                "    </marketplace>\n" +
                "    <creator>\n" +
                "        <email>andysen@gmail.com</email>\n" +
                "        <firstName>Andy</firstName>\n" +
                "        <lastName>Sen</lastName>\n" +
                "        <openId>https://www.acme-marketplace.com/openid/id/a11a7918-bb43-4429-a256-f6d729c71033</openId>\n" +
                "    </creator>\n" +
                "    <payload>\n" +
                "        <account>\n" +
                "            <accountIdentifier>MY_ACCOUNT</accountIdentifier>\n" +
                "        </account>\n" +
                "        <order>\n" +
                "            <editionCode>BASIC</editionCode>\n" +
                "            <item>\n" +
                "                <quantity>10</quantity>\n" +
                "                <unit>USER</unit>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <quantity>15</quantity>\n" +
                "                <unit>MEGABYTE</unit>\n" +
                "            </item>\n" +
                "        </order>\n" +
                "    </payload>\n" +
                "</event>").getBytes());
    }

    private ByteArrayInputStream aCancelSubscriptionOrderEventInputStream() {
        return new ByteArrayInputStream(("" +
                "<event>\n" +
                "    <type>SUBSCRIPTION_CANCEL</type>\n" +
                "    <marketplace>\n" +
                "        <partner>ACME</partner>\n" +
                "        <baseUrl>https://www.acme-marketplace.com</baseUrl>\n" +
                "    </marketplace>\n" +
                "    <creator>\n" +
                "        <email>andysen@gmail.com</email>\n" +
                "        <firstName>Andy</firstName>\n" +
                "        <lastName>Sen</lastName>\n" +
                "        <openId>https://www.acme-marketplace.com/openid/id/a11a7918-bb43-4429-a256-f6d729c71033</openId>\n" +
                "    </creator>\n" +
                "    <payload>\n" +
                "        <account>\n" +
                "            <accountIdentifier>MY_ACCOUNT</accountIdentifier>\n" +
                "        </account>\n" +
                "    </payload>\n" +
                "</event>").getBytes());
    }

    private ByteArrayInputStream aStatusSubscriptionOrderEventInputStream() {
        return new ByteArrayInputStream(("<event>\n" +
                "    <type>SUBSCRIPTION_NOTICE</type>\n" +
                "    <marketplace>\n" +
                "        <partner>ACME</partner>\n" +
                "        <baseUrl>https://www.acme-marketplace.com</baseUrl>\n" +
                "    </marketplace>\n" +
                "    <payload>\n" +
                "        <account>\n" +
                "            <accountIdentifier>MY_ACCOUNT</accountIdentifier>\n" +
                "            <status>FREE_TRIAL_EXPIRED</status>\n" +
                "        </account>\n" +
                "        <notice>\n" +
                "            <type>DEACTIVATED</type>\n" +
                "        </notice>\n" +
                "    </payload>\n" +
                "</event>").getBytes());
    }

    private void verifyCreateSubscriptionOrderEvent(CreateSubscriptionOrderEvent createSubscriptionOrderEvent) {
        assertThat(createSubscriptionOrderEvent.getType(), is(SUBSCRIPTION_ORDER));
        verifyMarketPlace(createSubscriptionOrderEvent.getMarketplace());
        verifyCreatorWithLanguage(createSubscriptionOrderEvent.getCreator());
        verifyCreateSubscriptionOrderPayload(createSubscriptionOrderEvent.getPayload());
    }

    private void verifyChangeSubscriptionOrderEvent(ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent) {
        assertThat(changeSubscriptionOrderEvent.getType(), is(SUBSCRIPTION_CHANGE));
        verifyMarketPlace(changeSubscriptionOrderEvent.getMarketplace());
        verifyCreator(changeSubscriptionOrderEvent.getCreator());
        verifyChangeSubscriptionOrderPayload(changeSubscriptionOrderEvent.getPayload());
    }

    private void verifyCancelSubscriptionOrderEvent(CancelSubscriptionOrderEvent cancelSubscriptionOrderEvent) {
        assertThat(cancelSubscriptionOrderEvent.getType(), is(SUBSCRIPTION_CANCEL));
        verifyMarketPlace(cancelSubscriptionOrderEvent.getMarketplace());
        verifyCreator(cancelSubscriptionOrderEvent.getCreator());
        verifyCancelSubscriptionOrderPayload(cancelSubscriptionOrderEvent.getPayload());
    }

    private void verifyStatusSubscriptionOrderEvent(StatusSubscriptionOrderEvent statusSubscriptionOrderEvent) {
        assertThat(statusSubscriptionOrderEvent.getType(), is(SUBSCRIPTION_NOTICE));
        verifyMarketPlace(statusSubscriptionOrderEvent.getMarketplace());
        verifyStatusSubscriptionOrderPayload(statusSubscriptionOrderEvent.getPayload());
    }

    private void verifyCreateSubscriptionOrderPayload(CreateSubscriptionOrderPayload payload) {
        verifyCompany(payload.getCompany());
        verifyOrder(payload.getOrder());
    }

    private void verifyChangeSubscriptionOrderPayload(ChangeSubscriptionOrderPayload payload) {
        assertThat(payload.getAccount(), is(anDefaultAccount()));
        verifyOrder(payload.getOrder());
    }

    private void verifyCancelSubscriptionOrderPayload(CancelSubscriptionOrderPayload payload) {
        assertThat(payload.getAccount(), is(anDefaultAccount()));
    }

    private void verifyStatusSubscriptionOrderPayload(StatusSubscriptionOrderPayload payload) {
        assertThat(payload.getAccount(), is(anDefaultAccount()));
        assertThat(payload.getNotice(), is(aDefaultNotice()));
    }

    private OrderItem anOrderItem(int quantity, OrderUnit unit) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setUnit(unit);
        return orderItem;
    }

    private void verifyMarketPlace(MarketPlace marketplace) {
        assertThat(marketplace.getPartner(), is("ACME"));
        assertThat(marketplace.getBaseUrl(), is("https://www.acme-marketplace.com"));
    }

    private void verifyCreatorWithLanguage(Contact creator) {
        verifyCreator(creator);
        assertThat(creator.getLanguage(), is("en"));
    }

    private void verifyCreator(Contact creator) {
        assertThat(creator.getEmail(), is("andysen@gmail.com"));
        assertThat(creator.getFirstName(), is("Andy"));
        assertThat(creator.getLastName(), is("Sen"));
        assertThat(creator.getOpenId(), is("https://www.acme-marketplace.com/openid/id/a11a7918-bb43-4429-a256-f6d729c71033"));
    }

    private void verifyCompany(Company company) {
        assertThat(company.getUuid(), is("d15bb36e-5fb5-11e0-8c3c-00262d2cda03"));
        assertThat(company.getEmail(), is("admin@example.com"));
        assertThat(company.getName(), is("Example Company"));
        assertThat(company.getPhoneNumber(), is("1-415-555-1212"));
        assertThat(company.getWebsite(), is("www.appdirect.com"));
    }

    private void verifyOrder(Order order) {
        assertThat(order.getItems(), containsInAnyOrder(anOrderItem(10, USER), anOrderItem(15, MEGABYTE)));
    }

    private Account anDefaultAccount() {
        Account account = new Account();
        account.setAccountIdentifier("MY_ACCOUNT");
        return account;
    }

    private Notice aDefaultNotice() {
        Notice notice = new Notice();
        notice.setType(DEACTIVATED);
        return notice;
    }
}