package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class ChangeSubscriptionOrderEvent extends Event<ChangeSubscriptionOrderPayload> {
    @Override
    public void setPayload(ChangeSubscriptionOrderPayload payload) {
        this.payload = payload;
    }

    @XmlElement
    @Override
    public ChangeSubscriptionOrderPayload getPayload() {
        return this.payload;
    }
}
