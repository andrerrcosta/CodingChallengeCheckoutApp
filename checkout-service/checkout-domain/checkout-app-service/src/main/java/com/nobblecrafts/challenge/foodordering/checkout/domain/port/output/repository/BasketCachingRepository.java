package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;

import java.util.Optional;

public interface BasketCachingRepository {

    Basket save(Basket basket);
    Optional<Basket> getCustomerBasket(CustomerId id);

    void remove(Basket basket);

}
