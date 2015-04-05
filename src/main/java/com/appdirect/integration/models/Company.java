package com.appdirect.integration.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.appdirect.integration.models.SubscriptionStatus.ACTIVE;

@Entity
@Table(name = "Company")
public class Company extends AbstractModel<Company> {
    private String name;
    private SubscriptionStatus status = ACTIVE;
    private EditionCode editionCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public EditionCode getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(EditionCode editionCode) {
        this.editionCode = editionCode;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", editionCode=" + editionCode +
                '}';
    }
}
