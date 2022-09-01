package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.repository;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByName(String name);

    @Modifying
    @Query(value = "UPDATE product SET price = :price WHERE id = :id", nativeQuery = true)
    ProductEntity updatePrice(@Param("price") BigDecimal price, @Param("id") UUID id);
}
