package com.appdirect.integration.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static com.appdirect.integration.models.SubscriptionStatus.ACTIVE;
import static org.hibernate.annotations.LazyCollectionOption.TRUE;

@Entity
@Table(name = "app_company", schema = "public")
public class Company extends AbstractModel<Company> {
    private String name;
    private SubscriptionStatus status = ACTIVE;
    private EditionCode editionCode;
    @LazyCollection(TRUE)
    @JsonIgnore
    @OneToMany(cascade= CascadeType.ALL, mappedBy="company")
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", editionCode=" + editionCode +
                ", users=" + users +
                '}';
    }
}
