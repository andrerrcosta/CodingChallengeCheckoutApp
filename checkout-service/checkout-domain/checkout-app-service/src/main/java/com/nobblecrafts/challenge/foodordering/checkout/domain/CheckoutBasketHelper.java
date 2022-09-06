package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;
import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutDomainException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDataMapper;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public BasketResponse persistBasket(Basket basket) {
        checkCustomer(basket.getCustomerId());
        var saved = saveBasket(checkoutDomainService.
                validateAndInitiateBasket(basket));
        log.info("basket created with id: {}", saved.getId().getValue());
        return mapper.toBasketResponse(saved);
    }

    private Basket saveBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    private void checkCustomer(CustomerId customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId.getValue());
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}", customerId);
            throw new CheckoutDomainException("Could not find customer with id: " + customer);
        }
    }
}
