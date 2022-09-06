package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrafts.challenge.foodordering.checkout.domain.objectvalue.BasketItemId;
import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.BasketId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@SuperBuilder
public class BasketItem extends BaseEntity<BasketItemId> {

    private BasketId basketId;
    private final String productName;
    private final String productId;
    @Builder.Default
    private Integer quantity = Integer.valueOf(0);
    private final Money price;
    @Builder.Default
    private final Set<Promotion> promotions = new HashSet<>();

    public void increaseQuantity() {
        this.quantity++;
    }

}
