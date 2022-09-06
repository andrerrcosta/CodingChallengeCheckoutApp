package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutBasketCommandHandler {

    private final CheckoutBasketHelper checkoutBasketHelper;
    private final CheckoutProductHelper checkoutProductHelper;

    @Transactional
    public BasketResponse checkout(BasketRequest basketRequest) {
        var basket = checkoutProductHelper.buildBasket(basketRequest);
        var response = checkoutBasketHelper.persistBasket(basket);
        return response;
    }


}
