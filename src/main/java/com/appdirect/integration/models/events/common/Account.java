package com.appdirect.integration.models.events.common;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class Account {
    @XmlElement
    private String accountIdentifier;

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountIdentifier != null ? !accountIdentifier.equals(account.accountIdentifier) : account.accountIdentifier != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return accountIdentifier != null ? accountIdentifier.hashCode() : 0;
    }
}
