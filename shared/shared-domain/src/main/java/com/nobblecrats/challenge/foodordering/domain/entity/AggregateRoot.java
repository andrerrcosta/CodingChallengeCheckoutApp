package com.nobblecrats.challenge.foodordering.domain.entity;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {
}
