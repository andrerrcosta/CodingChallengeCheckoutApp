package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.adapter;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper.BasketDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutNotFoundException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasketDaoRepository implements BasketRepository {


    private final CustomerJpaRepository customerJpaRepository;
    private final BasketDaoMapper mapper = BasketDaoMapper.INSTANCE;

    @Override
    public Basket save(Basket basket) {
        var customer = customerJpaRepository.findById(basket.getCustomerId().getValue())
                .orElseThrow(() -> new CheckoutNotFoundException("No customer found with id " +
                        basket.getCustomerId().getValue()));
        customer.addBasket(mapper.toBasketEntity(basket));
        customerJpaRepository.save(customer);
        return basket;
    }

}
