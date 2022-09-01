package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;

public interface CheckoutDomainService {
    Basket validateAndInitiateBasket(Basket basket);
}
