package com.appdirect.integration.models.events;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (quantity != orderItem.quantity) return false;
        if (unit != orderItem.unit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantity;
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
