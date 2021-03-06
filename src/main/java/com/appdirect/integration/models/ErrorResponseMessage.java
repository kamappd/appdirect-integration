package com.appdirect.integration.models;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "result")
@XmlAccessorType(FIELD)
public class ErrorResponseMessage extends ResponseMessage {
    @XmlElement
    private ResponseErrorCode errorCode;
    @XmlElement
    private String message;

    protected ErrorResponseMessage() {

    }

    public ErrorResponseMessage(ResponseErrorCode errorCode, String message) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ResponseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
