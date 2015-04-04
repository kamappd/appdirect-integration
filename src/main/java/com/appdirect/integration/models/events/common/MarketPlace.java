package com.appdirect.integration.models.events.common;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
public class MarketPlace {
    @XmlElement
    private String partner;
    @XmlElement
    private String baseUrl;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketPlace that = (MarketPlace) o;

        if (baseUrl != null ? !baseUrl.equals(that.baseUrl) : that.baseUrl != null) return false;
        if (partner != null ? !partner.equals(that.partner) : that.partner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partner != null ? partner.hashCode() : 0;
        result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0);
        return result;
    }
}
