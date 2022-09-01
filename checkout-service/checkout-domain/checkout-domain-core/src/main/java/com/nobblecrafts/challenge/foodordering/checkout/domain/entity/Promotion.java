package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.PromotionId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class Promotion extends BaseEntity<PromotionId> {
    @Builder.Default
    private Set<Product> products = new HashSet();
    private PromotionType type;
    // for BUY_X_GET_Y_FREE
    private Integer requiredQuantity;
    private Integer minQuantity;
    // for FLAT_PERCENT
    private Float amount;
    // for QTY_BASED_PRICE_OVERRIDE
    private Money price;
}
