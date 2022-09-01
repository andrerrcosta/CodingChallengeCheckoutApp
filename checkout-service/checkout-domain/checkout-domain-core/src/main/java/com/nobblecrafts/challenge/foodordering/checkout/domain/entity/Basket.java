package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutDomainException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.objectvalue.BasketItemId;
import com.nobblecrats.challenge.foodordering.domain.entity.AggregateRoot;
import com.nobblecrats.challenge.foodordering.domain.entity.BaseEntity;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.BasketId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
public class Basket extends AggregateRoot<BasketId> {

    private final CustomerId customerId;
    private final Money price;
    private final List<BasketItem> items;

    private Money promo;
    private Money totalPayable;

    public void initializeBasket() {
        setId(new BasketId(UUID.randomUUID()));
        initializeBasketItems();
    }

    public void validateBasket() {
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new CheckoutDomainException("Total price must be greater than zero!");
        }
    }

    private void validateItemsPrice() {
        Money basketItemsTotal = items.stream().map(item -> {
            validateItemPrice(item);
            return item.getPrice();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(basketItemsTotal)) {
            throw new CheckoutDomainException("Total price: " + price.getAmount()
                    + " is not equal to basket items total: " + basketItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(BasketItem item) {
        if (!item.isPriceValid()) {
            throw new CheckoutDomainException("Basket item price: " + item.getPrice().getAmount() +
                    " is not valid for product " + item.getProduct().getId().getValue());
        }
    }

    private void initializeBasketItems() {
        long itemId = 1;
        for (BasketItem item: items) {
            item.initializeBasketItem(super.getId(), new BasketItemId(itemId++));
        }
    }

}
