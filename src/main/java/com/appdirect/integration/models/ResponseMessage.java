package com.appdirect.integration.models;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "result")
@XmlAccessorType(FIELD)
public class ResponseMessage {
    @XmlElement
    private boolean success = true;
    @XmlElement
    private String message;
    @XmlElement
    private String accountIdentifier;

    protected ResponseMessage () {

    }

    public ResponseMessage(boolean success, String message, String accountIdentifier) {
        this.success = success;
        this.message = message;
        this.accountIdentifier = accountIdentifier;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
