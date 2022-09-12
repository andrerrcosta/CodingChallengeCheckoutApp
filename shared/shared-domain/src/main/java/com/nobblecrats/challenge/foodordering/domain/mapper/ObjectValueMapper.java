package com.nobblecrats.challenge.foodordering.domain.mapper;

import com.nobblecrats.challenge.foodordering.domain.objectvalue.*;

import java.math.BigDecimal;
import java.util.UUID;

public class ObjectValueMapper {

    public UUID toUUID(BaseId<UUID> baseId) {
        return baseId.getValue();
    }

    public PromotionId toPromotionId(String id) {
        return new PromotionId(id);
    }

    public BasketId toBasketId(UUID id) {
        return new BasketId(id);
    }

    public CustomerId toCustomerId(long id) {
        return new CustomerId(id);
    }

    public ProductId toProductId(String id) {
        return new ProductId(id);
    }

    public Money bigDecimalToMoney(BigDecimal amount) {
        return new Money(amount);
    }

    public BigDecimal moneyToBigDecimal(Money money) {
        return money.getAmount();
    }

}
