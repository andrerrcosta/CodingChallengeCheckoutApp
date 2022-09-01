package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;
    @Builder.Default
    private Set<Promotion> promotions = new HashSet<>();
}
