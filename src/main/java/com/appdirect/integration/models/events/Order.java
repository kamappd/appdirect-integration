package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class Order {
    @XmlElement
    private EditionCode editionCode;
    @XmlElement
    private PricingDuration pricingDuration;
    @XmlElement(name = "item")
    private List<OrderItem> items;

    public EditionCode getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(EditionCode editionCode) {
        this.editionCode = editionCode;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public PricingDuration getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(PricingDuration pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (editionCode != order.editionCode) return false;
        if (items != null ? !items.equals(order.items) : order.items != null) return false;
        if (pricingDuration != order.pricingDuration) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = editionCode != null ? editionCode.hashCode() : 0;
        result = 31 * result + (pricingDuration != null ? pricingDuration.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "editionCode=" + editionCode +
                ", pricingDuration=" + pricingDuration +
                ", items=" + items +
                '}';
    }
}
