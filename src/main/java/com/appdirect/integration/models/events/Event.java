package com.appdirect.integration.models.events;

import com.appdirect.integration.models.events.common.Contact;
import com.appdirect.integration.models.events.common.MarketPlace;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlTransient
public abstract class Event<T> {
    private EventType type;
    private MarketPlace marketplace;
    private Contact creator;
    private T payload;

    @XmlElement
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @XmlElement
    public MarketPlace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(MarketPlace marketplace) {
        this.marketplace = marketplace;
    }

    @XmlElement
    public Contact getCreator() {
        return creator;
    }

    public void setCreator(Contact creator) {
        this.creator = creator;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
