package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.PromotionType;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promotion")
@Entity
public class PromotionEntity {
    @Id
    private UUID id;

    @Builder.Default
    @ManyToMany(mappedBy = "promotions")
    @JsonBackReference(value = "product-promotions")
    @ToString.Exclude
    private Set<ProductEntity> products = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @NotNull
    private PromotionType type;

    // for BUY_X_GET_Y_FREE
    private Integer requiredQuantity;
    private Integer minQuantity;
    // for FLAT_PERCENT
    private Float amount;
    // for QTY_BASED_PRICE_OVERRIDE
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionEntity that = (PromotionEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
