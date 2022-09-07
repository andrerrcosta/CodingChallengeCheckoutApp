package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("promotion_redis")
@ToString
public class PromotionRedisEntity {
    @Id
    private String id;

    private String type;
    // for BUY_X_GET_Y_FREE
    private Integer requiredQuantity;
    private Integer freeQuantity;
    // for FLAT_PERCENT
    private BigDecimal amount;
    // for QTY_BASED_PRICE_OVERRIDE
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionRedisEntity that = (PromotionRedisEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
