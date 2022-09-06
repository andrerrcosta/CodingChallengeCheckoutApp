package com.nobblecrafts.challenge.foodordering.checkout.domain.port.input.service;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;

public interface CheckoutApplicationService {
    BasketResponse checkoutBasket(BasketRequest request);
}
