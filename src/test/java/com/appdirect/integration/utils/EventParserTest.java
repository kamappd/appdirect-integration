package com.appdirect.integration.utils;

import com.appdirect.integration.models.events.CancelSubscriptionOrderEvent;
import com.appdirect.integration.models.events.ChangeSubscriptionOrderEvent;
import com.appdirect.integration.models.events.CreateSubscriptionOrderEvent;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class EventParserTest {

    private EventParser eventParser = new EventParser();

    @Test
    public void can_parse_create_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aCreateSubscriptionOrderEventInputStream();

        CreateSubscriptionOrderEvent createSubscriptionOrderEvent = eventParser.parse(input, CreateSubscriptionOrderEvent.class);

        assertThat(createSubscriptionOrderEvent, notNullValue());
    }

    @Test
    public void can_parse_change_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aChangeSubscriptionOrderEventInputStream();

        ChangeSubscriptionOrderEvent changeSubscriptionOrderEvent = eventParser.parse(input, ChangeSubscriptionOrderEvent.class);

        assertThat(changeSubscriptionOrderEvent, notNullValue());
    }

    @Test
    public void can_parse_cancel_subscription_order_event() throws Exception {

        ByteArrayInputStream input = aCancelSubscriptionOrderEventInputStream();

        CancelSubscriptionOrderEvent cancelSubscriptionOrderEvent = eventParser.parse(input, CancelSubscriptionOrderEvent.class);

        assertThat(cancelSubscriptionOrderEvent, notNullValue());
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
}