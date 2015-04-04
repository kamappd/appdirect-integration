package com.appdirect.integration.models.events.common;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class Company {
    @XmlElement
    private String uuid;
    @XmlElement
    private String email;
    @XmlElement
    private String name;
    @XmlElement
    private String phoneNumber;
    @XmlElement
    private String website;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (email != null ? !email.equals(company.email) : company.email != null) return false;
        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(company.phoneNumber) : company.phoneNumber != null) return false;
        if (uuid != null ? !uuid.equals(company.uuid) : company.uuid != null) return false;
        if (website != null ? !website.equals(company.website) : company.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }
}
