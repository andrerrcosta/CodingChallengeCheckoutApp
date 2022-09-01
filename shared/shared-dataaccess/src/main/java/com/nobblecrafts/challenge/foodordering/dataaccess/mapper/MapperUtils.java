package com.nobblecrafts.challenge.foodordering.dataaccess.mapper;

import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;

import java.math.BigDecimal;

public class MapperUtils {
    public Money bigDecimalToMoney(BigDecimal amount) {
        return new Money(amount);
    }

    public BigDecimal moneyToBigDecimal(Money money) {
        return money.getAmount();
    }
}
