package com.appdirect.integration.models;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "result")
@XmlAccessorType(FIELD)
public class SuccessResponseMessage extends ResponseMessage {
    @XmlElement
    private String message;
    @XmlElement
    private String accountIdentifier;

    public SuccessResponseMessage() {
        this.success = true;
    }

    public SuccessResponseMessage(String accountIdentifier) {
        this();
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
