package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductRedisEntity;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("basket_redis")
@ToString
public class BasketRedisEntity {
    @Id
    private String id;

    private UUID basketId;

    @Builder.Default
    private Set<ProductRedisEntity> items = new HashSet<>();

    private BigDecimal price;

    private BigDecimal promo;

    private BigDecimal totalPayable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketRedisEntity that = (BasketRedisEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
