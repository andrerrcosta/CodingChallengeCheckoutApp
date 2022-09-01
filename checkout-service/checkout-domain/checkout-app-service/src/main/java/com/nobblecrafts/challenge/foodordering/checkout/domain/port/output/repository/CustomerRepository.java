package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);
}
