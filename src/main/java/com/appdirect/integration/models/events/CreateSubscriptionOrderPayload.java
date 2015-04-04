package com.appdirect.integration.models.events;

import com.appdirect.integration.models.events.common.Company;
import com.appdirect.integration.models.events.common.Order;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class CreateSubscriptionOrderPayload {
    @XmlElement
    private Company company;
    @XmlElement
    private Order order;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateSubscriptionOrderPayload that = (CreateSubscriptionOrderPayload) o;

        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
