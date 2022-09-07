package com.nobblecrafts.challenge.foodordering.checkout.app.rest;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.input.service.CheckoutApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/vnd.api.v1+json")
public class CheckoutController {

    private final CheckoutApplicationService service;

    @PostMapping(value = "/add-item")
    public ResponseEntity<BasketResponse> addItem(@RequestBody @Valid BasketItemRequest request) {
        log.info("Adding item for customer {}", request.customerId());
        BasketResponse response = service.addItem(request);
        log.info("item added for customer {}", request.customerId());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/checkout/{id}")
    public ResponseEntity<BasketResponse> checkout(@PathVariable("id") Long id) {
        log.info("Checking basket for customer {}", id);
        BasketResponse response = service.checkoutBasket(id);
        log.info("Basket checked for customer {}", id);
        return ResponseEntity.ok(response);
    }

}
