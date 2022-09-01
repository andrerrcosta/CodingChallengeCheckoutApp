package com.nobblecrafts.challenge.foodordering.checkout.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutBasketCommandHandler {

    private final CheckoutBasketHelper checkoutBasketHelper;


}
