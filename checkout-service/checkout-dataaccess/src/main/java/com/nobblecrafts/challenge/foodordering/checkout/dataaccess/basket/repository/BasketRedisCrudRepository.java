package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BasketRedisCrudRepository extends CrudRepository<BasketRedisEntity, String> {
    Set<BasketRedisEntity> findAll();
}
