package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.entity.AggregateRoot;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.BasketId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@SuperBuilder
@Slf4j
public class Basket extends AggregateRoot<BasketId> {

    private final CustomerId customerId;
    @Builder.Default
    private Money price = new Money(new BigDecimal("0"));
    @Builder.Default
    private final List<BasketItem> items = new ArrayList<>();
    @Builder.Default
    private Money promo = new Money(new BigDecimal("0"));
    @Builder.Default
    private Money totalPayable = new Money(new BigDecimal("0"));

    public void addItem(BasketItem item) {
        log.info("\n\nBefore Add To List: {}", item);
        var added = this.addItemToList(item);
        log.info("\n\n{} is empty?", added.getProductName());
        log.info("\n\n{}\n\n", added);
        if (!added.getPromotions().isEmpty()) {
            verifyPromotion(added);
        }
    }

    public void initializeBasket() {
        setId(new BasketId(UUID.randomUUID()));
    }

    private BasketItem addItemToList(BasketItem item) {
        log.info("\n\nADD-ITEM-TO-LIST {}", item);

        var optional = items.stream().filter(i -> i.getProductId().equals(item.getProductId())).findFirst();
        if (optional.isEmpty()) {
            log.info("\n\nEMPTY: {}\n\n", items);
            this.items.add(item);
            item.setBasketId(this.getId());
            item.increaseQuantity();
            this.price = this.price.add(item.getPrice());
            this.totalPayable = this.totalPayable.add(item.getPrice());
            return item;
        } else {
            log.info("\n\nNOT-EMPTY: {}", items);
            var output = optional.get();
            log.info("\n\nOptional Get: {}\n\n", optional.get());
            output.increaseQuantity();
            this.price = this.price.add(item.getPrice());
            this.totalPayable = this.totalPayable.add(item.getPrice());
            return output;
        }
    }


    /**
     * There is no enough information of how to
     * aplly more than one promotion over
     * the same product. The api returns
     * an array of promotions, so I'm taking
     * the first promotion as the valid one.
     *
     * @param item
     */
    private void verifyPromotion(BasketItem item) {
        var optional = item.getPromotions().stream().findFirst();
        if (!optional.isEmpty()) {
            var promotion = optional.get();
            switch (promotion.getType()) {
                case QTY_BASED_PRICE_OVERRIDE -> {
                    log.info("\n\nQTY_BASED_PRICE_OVERRIDE?: {}\n\n", item.getProductName());
                    log.info("\n\n{} % {}\n\n", item.getQuantity(), promotion.getRequiredQuantity());
                    var rest = item.getQuantity() % promotion.getRequiredQuantity();
                    if (rest == 0) {
                        log.info("\n\nYES?: {}\n\n", item.getProductName());
                        var replacePrice = item.getPrice().multiply(promotion.getRequiredQuantity());
                        this.totalPayable = totalPayable
                                .subtract(replacePrice)
                                .add(promotion.getPrice());
                        this.promo = this.price.subtract(this.totalPayable);
                    }
                }

                case FLAT_PERCENT -> {
                    this.totalPayable = this.totalPayable.subtract(item.getPrice())
                            .add(item.getPrice().percent(Money.ONE_HUNDRED.subtract(promotion.getAmount())));
                    this.promo = this.price.subtract(this.totalPayable);
                }


                case BUY_X_GET_Y_FREE -> {
                    var rest = item.getQuantity() % promotion.getRequiredQuantity();

                    if (rest == 0) {
                        this.totalPayable = this.totalPayable.subtract(item.getPrice());
                        this.promo = this.price.subtract(this.totalPayable);
                    }
                }
            }

        }
    }

}
