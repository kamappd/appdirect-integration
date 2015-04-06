package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class StatusSubscriptionOrderEvent extends Event<StatusSubscriptionOrderPayload> {

    @XmlElement
    @Override
    public StatusSubscriptionOrderPayload getPayload() {
        return payload;
    }

    @Override
    public void setPayload(StatusSubscriptionOrderPayload payload) {
        this.payload = payload;
    }
}
