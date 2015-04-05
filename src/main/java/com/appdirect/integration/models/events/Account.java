package com.appdirect.integration.models.events;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class Account {
    @XmlElement
    private String accountIdentifier;
    private AccountStatus status;

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountIdentifier != null ? !accountIdentifier.equals(account.accountIdentifier) : account.accountIdentifier != null)
            return false;
        if (status != account.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountIdentifier != null ? accountIdentifier.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountIdentifier='" + accountIdentifier + '\'' +
                ", status=" + status +
                '}';
    }
}
