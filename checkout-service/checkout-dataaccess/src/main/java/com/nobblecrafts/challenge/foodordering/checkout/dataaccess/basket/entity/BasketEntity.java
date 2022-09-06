package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
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
    private List<String> items = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-baskets")
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private BigDecimal price;

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

}
