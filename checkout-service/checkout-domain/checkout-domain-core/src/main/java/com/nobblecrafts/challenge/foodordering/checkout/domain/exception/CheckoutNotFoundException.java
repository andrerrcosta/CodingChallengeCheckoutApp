package com.nobblecrafts.challenge.foodordering.checkout.domain.exception;

import com.nobblecrats.challenge.foodordering.domain.exception.DomainException;

public class CheckoutNotFoundException extends DomainException {
    public CheckoutNotFoundException(String message) {
        super(message);
    }

    public CheckoutNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
