package com.appdirect.integration.models;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

public class SuccessResponseMessage extends ResponseMessage {
    @XmlElement
    private String message;
    @XmlElement
    private String accountIdentifier;

    protected SuccessResponseMessage() {

    }

    public SuccessResponseMessage(String message, String accountIdentifier) {
        this.success = true;
        this.message = message;
        this.accountIdentifier = accountIdentifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }
}
