package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Product;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.ProductId;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Product updatePrice(Money price, ProductId id);
    Optional<Product> findByName(String name);
}
