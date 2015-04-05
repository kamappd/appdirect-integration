package com.appdirect.integration.models;

import com.appdirect.integration.models.events.EditionCode;
import com.appdirect.integration.models.events.Order;

import static com.appdirect.integration.models.SubscriptionStatus.ACTIVE;

public class Subscription {
    private String id;
    private String companyName;
    private Order order;
    private SubscriptionStatus status = ACTIVE;
    private EditionCode editionCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", order=" + order +
                ", status=" + status +
                ", editionCode=" + editionCode +
                '}';
    }
}
