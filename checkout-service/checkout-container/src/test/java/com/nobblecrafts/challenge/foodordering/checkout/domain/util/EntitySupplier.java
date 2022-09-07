package com.nobblecrafts.challenge.foodordering.checkout.domain.util;

import com.github.javafaker.Faker;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity.PromotionRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.PromotionType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

public class EntitySupplier {

    private static final Faker faker;
    private static final Random random;

    static {
        faker = Faker.instance();
        random = new Random();
    }


    public static BasketRedisEntity basketRedisEntity() {
        var total = new BigDecimal(BigInteger.valueOf(random.nextInt(200)), 2);
        var payable = new BigDecimal(BigInteger.valueOf(random.nextInt(total.intValue())), 2);
        var promo = new BigDecimal(String.valueOf(total.subtract(payable)));
        promo.setScale(2);
        return BasketRedisEntity.builder()
                .basketId(UUID.randomUUID())
                .promo(promo)
                .price(total)
                .totalPayable(payable)
                .id(UUID.randomUUID().toString())
                .build();
    }

    public static ProductRedisEntity productRedisEntity() {
        return ProductRedisEntity.builder()
                .productId(UUID.randomUUID().toString())
                .productName(faker.commerce().productName())
                .quantity(random.nextInt(5))
                .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10)), 2))
                .build();
    }

    public static PromotionRedisEntity promotionRedisEntity() {
        var type = random.nextInt(3);
        PromotionRedisEntity output = null;
        switch (type) {
            case 0: {
                output = PromotionRedisEntity.builder()
                        .type(PromotionType.BUY_X_GET_Y_FREE.toString())
                        .requiredQuantity(2)
                        .freeQuantity(1)
                        .build();
                break;
            }
            case 1: {
                output = PromotionRedisEntity.builder()
                        .type(PromotionType.FLAT_PERCENT.toString())
                        .amount(new BigDecimal(BigInteger.valueOf(random.nextInt(50)), 2))
                        .build();
                break;
            }
            case 2: {

                output = PromotionRedisEntity.builder()
                        .type(PromotionType.QTY_BASED_PRICE_OVERRIDE.toString())
                        .requiredQuantity(2)
                        .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10)), 2))
                        .build();
                break;
            }
        }
        return output;
    }


}
