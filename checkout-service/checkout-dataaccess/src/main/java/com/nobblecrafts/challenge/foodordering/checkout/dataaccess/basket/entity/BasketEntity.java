package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
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
@Table(name = "basket")
@Entity
@ToString
public class BasketEntity {

    @Id
    private UUID id;

    @Builder.Default
    @ElementCollection
    private List<String> productIds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-baskets")
    @JoinColumn(name = "customer_id")
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
