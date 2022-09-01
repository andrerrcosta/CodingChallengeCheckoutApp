package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
}
