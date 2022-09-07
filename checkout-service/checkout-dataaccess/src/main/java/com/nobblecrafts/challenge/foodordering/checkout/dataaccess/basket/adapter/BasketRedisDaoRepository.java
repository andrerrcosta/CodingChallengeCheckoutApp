package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.adapter;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper.BasketDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketRedisCrudRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketCachingRepository;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BasketRedisDaoRepository implements BasketCachingRepository {

    private final BasketRedisCrudRepository repository;
    private final BasketDaoMapper mapper = BasketDaoMapper.INSTANCE;

    @Override
    public Basket save(Basket basket) {
        return mapper.toBasket(repository.save(mapper.toBasketRedisEntity(basket)));
    }

    @Override
    public Optional<Basket> getCustomerBasket(CustomerId id) {
        return repository.findById(mapper.toBasketRedisId(id))
                .map(mapper::toBasket);
    }

    @Override
    public void remove(Basket basket) {
        repository.delete(mapper.toBasketRedisEntity(basket));
    }
}
