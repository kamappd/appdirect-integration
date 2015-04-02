package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class CreateSubscriptionOrderEvent extends Event<CreateSubscriptionOrderPayload> {
    @Override
    public void setPayload(CreateSubscriptionOrderPayload payload) {
        super.setPayload(payload);
    }

    @Override
    @XmlElement
    public CreateSubscriptionOrderPayload getPayload() {
        return super.getPayload();
    }
}
