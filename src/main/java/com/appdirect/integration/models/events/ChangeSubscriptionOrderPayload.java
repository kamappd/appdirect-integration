package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class ChangeSubscriptionOrderPayload {
    @XmlElement
    private Account account;
    @XmlElement
    private Order order;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ChangeSubscriptionOrderPayload{" +
                "account=" + account +
                ", order=" + order +
                '}';
    }
}
