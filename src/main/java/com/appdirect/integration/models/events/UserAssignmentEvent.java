package com.appdirect.integration.models.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.PROPERTY;

@XmlRootElement(name = "event")
@XmlAccessorType(PROPERTY)
@Entity
@Table(name = "event", schema = "public")
@DiscriminatorValue("UserAssignmentEvent")
public class UserAssignmentEvent extends Event<UserAssignmentPayload> {
    @XmlElement
    @Override
    public UserAssignmentPayload getPayload() {
        return this.payload;
    }

    @Override
    public void setPayload(UserAssignmentPayload payload) {
        this.payload = payload;
    }
}
