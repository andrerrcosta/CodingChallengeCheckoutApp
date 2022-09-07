package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;
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
    public BasketResponse addItem(BasketItemRequest basketRequest) {
        var item = checkoutProductHelper.scanBasketItem(basketRequest);
        var basket = checkoutBasketHelper.recoverOrCreateBasket(basketRequest.customerId());
        basket.addItem(item);
        return checkoutBasketHelper.cacheBasket(basket);
    }

    @Transactional
    public BasketResponse checkout(Long customerId) {
        var basket = checkoutBasketHelper.recoverOrCreateBasket(customerId);
        return checkoutBasketHelper.checkout(basket);
    }


}
