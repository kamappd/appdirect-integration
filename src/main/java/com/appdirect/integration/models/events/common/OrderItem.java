package com.appdirect.integration.models.events.common;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class OrderItem {
    @XmlElement
    private int quantity;
    @XmlElement
    private OrderUnit unit;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderUnit getUnit() {
        return unit;
    }

    public void setUnit(OrderUnit unit) {
        this.unit = unit;
    }
}
