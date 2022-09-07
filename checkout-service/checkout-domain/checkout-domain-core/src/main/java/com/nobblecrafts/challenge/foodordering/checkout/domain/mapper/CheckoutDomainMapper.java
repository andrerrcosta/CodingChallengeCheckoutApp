package com.nobblecrafts.challenge.foodordering.checkout.domain.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.domain.objectvalue.BasketItemId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;

import java.util.UUID;

public class CheckoutDomainMapper {
    public BasketItemId toBasketItemId(String id) {
        return new BasketItemId(id);
    }

}
