package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.PromotionId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@ToString
public class Promotion extends BaseEntity<PromotionId> {

    private PromotionType type;
    // for BUY_X_GET_Y_FREE
    private Integer requiredQuantity;
    private Integer freeQuantity;
    // for FLAT_PERCENT
    private BigDecimal amount;
    // for QTY_BASED_PRICE_OVERRIDE
    private Money price;
}
