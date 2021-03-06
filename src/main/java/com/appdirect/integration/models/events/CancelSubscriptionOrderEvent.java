package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class CancelSubscriptionOrderEvent extends Event<CancelSubscriptionOrderPayload> {
    @Override
    public void setPayload(CancelSubscriptionOrderPayload payload) {
        this.payload = payload;
    }

    @XmlElement
    @Override
    public CancelSubscriptionOrderPayload getPayload() {
        return this.payload;
    }
}
