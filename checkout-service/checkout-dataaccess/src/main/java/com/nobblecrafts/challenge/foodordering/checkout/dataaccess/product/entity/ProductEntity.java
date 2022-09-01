package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity.PromotionEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity
public class ProductEntity {
    @Id
    private UUID id;
    @Builder.Default
    @ManyToMany(mappedBy = "items")
    @JsonBackReference(value = "basket-products")
    @ToString.Exclude
    private Set<BasketEntity> baskets = new HashSet<>();

    @Column(unique = true)
    private String name;
    private BigDecimal price;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "product-promotions")
    @JoinTable(
            name = "product_promotions",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<PromotionEntity> promotions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addPromotion(PromotionEntity promotion) {
        this.promotions.add(promotion);
        promotion.getProducts().add(this);
    }

    public void removePromotion(PromotionEntity promotion) {
        this.promotions.remove(promotion);
        promotion.getProducts().remove(this);
    }

    public void removeAllPromotions() {
        Iterator<PromotionEntity> iterator = this.promotions.iterator();
        while (iterator.hasNext()) {
            PromotionEntity promotion = iterator.next();
            promotion.getProducts().remove(this);
            iterator.remove();
        }
    }
}
