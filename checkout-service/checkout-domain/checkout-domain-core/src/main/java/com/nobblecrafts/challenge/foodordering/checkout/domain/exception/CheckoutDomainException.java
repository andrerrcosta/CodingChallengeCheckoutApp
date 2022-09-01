package com.nobblecrafts.challenge.foodordering.checkout.domain.exception;

import com.nobblecrats.challenge.foodordering.domain.exception.DomainException;

public class CheckoutDomainException extends DomainException {
    public CheckoutDomainException(String message) {
        super(message);
    }

    public CheckoutDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
