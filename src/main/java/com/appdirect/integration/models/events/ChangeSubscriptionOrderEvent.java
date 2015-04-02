package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class ChangeSubscriptionOrderEvent extends Event<ChangeSubscriptionOrderPayload> {
    @Override
    public void setPayload(ChangeSubscriptionOrderPayload payload) {
        super.setPayload(payload);
    }

    @XmlElement
    @Override
    public ChangeSubscriptionOrderPayload getPayload() {
        return super.getPayload();
    }
}
