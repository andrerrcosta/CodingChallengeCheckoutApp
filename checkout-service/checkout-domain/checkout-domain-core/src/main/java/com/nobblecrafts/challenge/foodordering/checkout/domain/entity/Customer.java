package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.entity.AggregateRoot;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Customer extends AggregateRoot<CustomerId> {
    private String name;
    private String email;
}
