package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasketJpaRepository extends JpaRepository<BasketEntity, UUID> {
}
