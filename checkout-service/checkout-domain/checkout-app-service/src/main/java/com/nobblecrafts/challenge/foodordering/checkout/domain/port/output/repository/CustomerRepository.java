package com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Set<Customer> findAll();
}
