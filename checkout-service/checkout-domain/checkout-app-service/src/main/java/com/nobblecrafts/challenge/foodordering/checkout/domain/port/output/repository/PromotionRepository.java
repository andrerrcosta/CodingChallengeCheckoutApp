package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Promotion;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PromotionRepository {
    Set<Promotion> findAll();
    Promotion add(Promotion promotion);
    Optional<Promotion> findById(UUID id);
    Optional<Promotion> findAllByBasket(BasketRequest request);
}
