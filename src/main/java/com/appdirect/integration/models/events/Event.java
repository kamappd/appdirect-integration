package com.appdirect.integration.models.events;

import com.appdirect.integration.models.events.common.Contact;
import com.appdirect.integration.models.events.common.MarketPlace;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "event")
@XmlAccessorType(FIELD)
public abstract class Event<T> {
    @XmlElement
    private EventType type;
    @XmlElement
    private MarketPlace marketplace;
    @XmlElement
    private Contact creator;
    @XmlElement
    private T payload;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public MarketPlace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(MarketPlace marketplace) {
        this.marketplace = marketplace;
    }

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
