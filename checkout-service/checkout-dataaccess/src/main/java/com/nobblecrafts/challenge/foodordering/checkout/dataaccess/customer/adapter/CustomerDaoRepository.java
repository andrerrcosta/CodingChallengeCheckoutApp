package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.adapter;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.mapper.CustomerDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.CustomerRepository;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomerDaoRepository implements CustomerRepository {

    private final CustomerJpaRepository repository;
    private final CustomerDaoMapper mapper = CustomerDaoMapper.INSTANCE;

    @Override
    public Customer save(Customer customer) {
        return mapper.toCustomer(repository.save(mapper.toCustomerEntity(customer)));
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return repository.findById(mapper.fromCustomerIdToLong(id))
                .map(mapper::toCustomer);
    }

    @Override
    public Set<Customer> findAll() {
        return mapper.toCustomer(repository.findAll());
    }
}
