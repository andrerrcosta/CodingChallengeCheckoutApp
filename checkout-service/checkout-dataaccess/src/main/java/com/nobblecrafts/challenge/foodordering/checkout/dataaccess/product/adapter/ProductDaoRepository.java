package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.adapter;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.mapper.ProductDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.repository.ProductJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Product;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.ProductId;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDaoRepository implements ProductRepository {

    private final ProductJpaRepository repository;
    private final ProductDaoMapper mapper = ProductDaoMapper.INSTANCE;

    @Override
    public Product save(Product product) {
        return mapper.toProduct(repository.save(mapper.toProductEntity(product)));
    }

    @Override
    public Product updatePrice(Money price, ProductId id) {
        return mapper.toProduct(repository.updatePrice(price.getAmount(), id.getValue()));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toProduct);
    }
}
