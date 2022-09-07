package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketJpaRepository extends JpaRepository<BasketEntity, UUID> {
}
