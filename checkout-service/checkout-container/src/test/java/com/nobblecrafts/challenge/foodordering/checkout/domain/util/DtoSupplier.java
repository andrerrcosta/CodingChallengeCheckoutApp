package com.nobblecrafts.challenge.foodordering.checkout.domain.util;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;

import java.util.Map;
import java.util.Random;

public class DtoSupplier {

    private static final Map<String, String> products;

    static {
        products = Map.of("Amazing Pizza", "Dwt5F7KAhi",
                "Amazing Burger", "PWWe3w1SDU",
                "Amazing Salad", "C8GDyLrHJb",
                "Boring Fries", "4MB7UfpTQs");
    }

    public static BasketItemRequest randomBasketItemRequest(Long customerId) {
        Random generator = new Random();
        Object[] values = products.values().toArray();
        String productId = String.valueOf(values[generator.nextInt(values.length)]);
        return BasketItemRequest.builder()
                .customerId(customerId)
                .productId(productId)
                .build();
    }

    public static BasketItemRequest basketItemRequest(Long customerId, String product) {
        return BasketItemRequest.builder()
                .customerId(customerId)
                .productId(products.get(product))
                .build();
    }
}
