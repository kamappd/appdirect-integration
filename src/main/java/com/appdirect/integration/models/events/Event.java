package com.appdirect.integration.models.events;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (creator != null ? !creator.equals(event.creator) : event.creator != null) return false;
        if (marketplace != null ? !marketplace.equals(event.marketplace) : event.marketplace != null) return false;
        if (payload != null ? !payload.equals(event.payload) : event.payload != null) return false;
        if (type != event.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (marketplace != null ? marketplace.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", marketplace=" + marketplace +
                ", creator=" + creator +
                ", payload=" + payload +
                '}';
    }
}
