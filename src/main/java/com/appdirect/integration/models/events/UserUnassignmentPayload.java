package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class UserUnassignmentPayload {

    @XmlElement
    private Account account;
    @XmlElement
    private Contact user;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Contact getUser() {
        return user;
    }

    public void setUser(Contact user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AssignmentPayload{" +
                "account=" + account +
                ", user=" + user +
                '}';
    }
}
