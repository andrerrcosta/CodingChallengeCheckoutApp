package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;

public class CheckoutDomainServiceImpl implements CheckoutDomainService {
    @Override
    public Basket validateAndInitiateBasket(Basket basket) {
        basket.validateBasket();
        basket.initializeBasket();
        return basket;
    }
}
