package com.appdirect.integration.exceptions;

import com.appdirect.integration.models.ErrorResponseMessage;

public class SubscriptionOrderEventException extends RuntimeException {
    private ErrorResponseMessage errorResponseMessage;

    public SubscriptionOrderEventException(ErrorResponseMessage errorResponseMessage) {
        this.errorResponseMessage = errorResponseMessage;
    }

    public ErrorResponseMessage getErrorResponseMessage() {
        return errorResponseMessage;
    }
}
