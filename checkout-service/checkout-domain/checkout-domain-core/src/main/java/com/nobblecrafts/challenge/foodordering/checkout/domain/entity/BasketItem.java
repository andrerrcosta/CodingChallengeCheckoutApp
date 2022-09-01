package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrafts.challenge.foodordering.checkout.domain.objectvalue.BasketItemId;
import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.BasketId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BasketItem extends BaseEntity<BasketItemId> {
    private BasketId basketId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    void initializeBasketItem(BasketId basketId, BasketItemId basketItemId) {
        this.basketId = basketId;
        super.setId(basketItemId);
    }

    public boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }
}
