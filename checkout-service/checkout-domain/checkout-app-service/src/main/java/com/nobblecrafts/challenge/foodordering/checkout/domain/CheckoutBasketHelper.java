package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;
import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutDomainException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDataMapper;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    @Transactional
    public BasketResponse persistBasket(BasketRequest request) {
        checkCustomer(request.customerId());
        var basket = checkoutDomainService.
                validateAndInitiateBasket(mapper.toBasket(request));
        var saved = saveBasket(basket);
        log.info("basket is created with id: {}", saved.getId().getValue());

    }

    private Basket saveBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    private void checkCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new CheckoutDomainException("Could not find customer with customer id: " + customer);
        }
    }
}
