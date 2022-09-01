package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;

public interface BasketRepository {
    Basket save(Basket basket);

}
