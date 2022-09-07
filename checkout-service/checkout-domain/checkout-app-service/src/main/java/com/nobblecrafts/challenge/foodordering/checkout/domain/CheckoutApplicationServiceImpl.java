package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.input.service.CheckoutApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CheckoutApplicationServiceImpl implements CheckoutApplicationService {

    private final CheckoutBasketCommandHandler checkoutBasketCommandHandler;

    @Override
    public BasketResponse checkoutBasket(Long customerId) {
        return checkoutBasketCommandHandler.checkout(customerId);
    }

    @Override
    public BasketResponse addItem(BasketItemRequest request) {
        return checkoutBasketCommandHandler.addItem(request);
    }
}
