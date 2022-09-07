package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;
import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutNotFoundException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDataMapper;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketCachingRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.CustomerRepository;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutBasketHelper {
    private final BasketRepository basketRepository;
    private final CustomerRepository customerRepository;
    private final CheckoutDataMapper mapper = CheckoutDataMapper.INSTANCE;
    private final CheckoutDomainService checkoutDomainService;
    private final BasketCachingRepository basketCachingRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Basket recoverOrCreateBasket(Long customerId) {
        checkCustomer(customerId);
        var basket = basketCachingRepository.getCustomerBasket(new CustomerId(customerId));
        if (basket.isEmpty()) {
            return basketCachingRepository.save(Basket.builder()
                    .customerId(new CustomerId(customerId))
                    .build());
        }
        return basket.get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BasketResponse cacheBasket(Basket basket) {
        var saved = basketCachingRepository.save(checkoutDomainService.
                validateAndInitiateBasket(basket));
        log.info("basket created with id: {}", saved.getId().getValue());
        return mapper.toBasketResponse(saved);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BasketResponse checkout(Basket basket) {
        var entity = basketRepository.save(basket);
        basketCachingRepository.remove(basket);
        return mapper.toBasketResponse(entity);
    }

    public void addItemToBasket() {
        /**
         * Maybe use some functional interface inside
         * the Basket to apply promotions through implementations
         * of a utilitarian interface
         */
    }

    private void checkCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(new CustomerId(customerId));
        if (customer.isEmpty()) {
            log.warn("Unknown customer with id: {}", customerId);
            throw new CheckoutNotFoundException("Could not find customer with id: " + customerId);
        }
    }
}
