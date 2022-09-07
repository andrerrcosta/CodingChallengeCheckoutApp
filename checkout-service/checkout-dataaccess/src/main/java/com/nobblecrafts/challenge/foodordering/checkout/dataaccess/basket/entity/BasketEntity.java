package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "basket", indexes = {
        @Index(name = "bkt_ctm_index", columnList = "customer_id")
})
@Entity
@ToString
public class BasketEntity {

    @Id
    private UUID id;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "basket_products", joinColumns = @JoinColumn(name = "basket_id"))
    @Column(name = "product_id", nullable = false)
    private List<String> productIds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-baskets")
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    private BigDecimal total;

    private BigDecimal totalPayable;

    private BigDecimal totalPromos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketEntity that = (BasketEntity) o;
        return id.equals(that.id) && customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer);
    }


    public void addItems(String itemId, Integer amount) {
        for(var i = 0; i < amount; i++) {
            productIds.add(itemId);
        }
    }
}
