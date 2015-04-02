package com.appdirect.integration.models.events;

import com.appdirect.integration.models.events.common.Company;
import com.appdirect.integration.models.events.common.Order;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

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
}
