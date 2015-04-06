package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
public class UserUnassignmentEvent extends Event<UserUnassignmentPayload> {
    @XmlElement
    @Override
    public UserUnassignmentPayload getPayload() {
        return this.payload;
    }

    @Override
    public void setPayload(UserUnassignmentPayload payload) {
        this.payload = payload;
    }
}
