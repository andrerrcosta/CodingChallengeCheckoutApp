package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity.PromotionRedisEntity;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "product_redis")
@ToString
public class ProductRedisEntity {
    @Id
    private String id;
    private UUID basketId;
    private String productName;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
    @Builder.Default
    private Set<PromotionRedisEntity> promotions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRedisEntity that = (ProductRedisEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
