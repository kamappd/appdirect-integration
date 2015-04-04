package com.appdirect.integration.models.events;

import com.appdirect.integration.models.events.common.Contact;
import com.appdirect.integration.models.events.common.MarketPlace;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@XmlTransient
@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "class")
public abstract class Event<T> {
    private EventType type;
    private MarketPlace marketplace;
    private Contact creator;
    protected T payload;

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

    public abstract T getPayload();

    public abstract void setPayload(T payload);
}
