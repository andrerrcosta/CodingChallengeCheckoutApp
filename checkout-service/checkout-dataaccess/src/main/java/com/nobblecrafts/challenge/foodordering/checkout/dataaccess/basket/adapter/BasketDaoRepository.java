package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.adapter;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper.BasketDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasketDaoRepository implements BasketRepository {

    private final BasketJpaRepository repository;
    private final BasketDaoMapper mapper = BasketDaoMapper.INSTANCE;

    @Override
    public Basket save(Basket basket) {
        repository
                .save(mapper.toBasketEntity(basket));
        return basket;
    }
}
