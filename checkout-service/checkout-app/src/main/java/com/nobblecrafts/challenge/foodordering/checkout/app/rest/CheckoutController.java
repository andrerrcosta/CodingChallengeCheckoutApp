package com.nobblecrafts.challenge.foodordering.checkout.app.rest;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.input.service.CheckoutApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/checkout", produces = "application/vnd.api.v1+json")
public class CheckoutController {

    private final CheckoutApplicationService service;

    @PostMapping
    public ResponseEntity<BasketResponse> checkoutBasket(@RequestBody BasketRequest request) {
        log.info("Checking basket for customer {}", request.customerId());
        BasketResponse response = service.checkoutBasket(request);
        log.info("Basket checked for customer {}", request.customerId());
        return ResponseEntity.ok(response);
    }

}
